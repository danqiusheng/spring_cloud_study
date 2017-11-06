package com.moa.cloud.command;

import com.moa.cloud.model.User;
import com.moa.cloud.service.UserService;
import com.netflix.hystrix.HystrixCollapser;
import com.netflix.hystrix.HystrixCollapserKey;
import com.netflix.hystrix.HystrixCollapserProperties;
import com.netflix.hystrix.HystrixCommand;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Administrator on 2017/11/5.
 */
public class UserCollapseCommand extends HystrixCollapser<List<User>,User,String> {


    private UserService userService;

    private  String userId;


    public UserCollapseCommand(UserService userService,String userId){
        super(HystrixCollapser.Setter.withCollapserKey
                (HystrixCollapserKey.Factory.asKey("userCollapseCommand"))
                .andCollapserPropertiesDefaults(HystrixCollapserProperties.Setter()
                        .withTimerDelayInMilliseconds(5000)));
        this.userService = userService;
        this.userId = userId;
    }

    @Override
    public String getRequestArgument() {
        return userId;
    }


    /**
     * 创建统一的请求
     * @param collection
     * @return
     */
    @Override
    protected HystrixCommand<List<User>> createCommand(Collection<CollapsedRequest<User, String>> collection) {
        List<String> userIds = new ArrayList<String>(collection.size());
        userIds.addAll(collection.stream().map(CollapsedRequest::getArgument).collect(Collectors.toList()));
        return new UserBatchCommand(userService,userIds);
    }

    /**
     * 对结果进行统一的返回
     * @param users
     * @param collection
     */
    @Override
    protected void mapResponseToRequests(List<User> users, Collection<CollapsedRequest<User, String>> collection) {
        int count = 0;
        for(CollapsedRequest<User,String> collapsedRequest : collection){
            System.out.println(users);
            User user = users.get(count++);
            collapsedRequest.setResponse(user);
        }
    }
}
