package com.moa.cloud.command;

import com.moa.cloud.model.User;
import com.moa.cloud.service.UserService;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

import java.util.List;

/**
 * Created by Administrator on 2017/11/5.
 */
public class UserBatchCommand extends HystrixCommand<List<User>> {

    private UserService userService;

    private List<String> userIds;



    public UserBatchCommand(UserService userService,List<String> userIds) {
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("userServiceCommand")));
        this.userIds = userIds;
        this.userService = userService;
    }

    @Override
    protected List<User> run() throws Exception {
        System.out.println("findAll");
        System.out.println(userIds.toString());
        List<User> list =  userService.findAll(userIds);
        System.out.println(list);
        return list;
    }
}
