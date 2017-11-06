package com.moa.cloud.command;

import com.moa.cloud.model.User;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import org.springframework.web.client.RestTemplate;

/**
 * Created by Administrator on 2017/11/3.
 */
public class UserPostCommand extends HystrixCommand<User> {


    private RestTemplate restTemplate;
    private User user;

    public UserPostCommand(RestTemplate restTemplate,User user) {
        // 设置组名和命令名
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("GetSetGet")));
        this.restTemplate = restTemplate;
        this.user = user;
    }

    @Override
    protected User run() throws Exception {
       User r  = restTemplate.postForObject("http://CLOUD-DEMO-SERVICE/users",user,User.class);
       // 刷新缓存，清除缓存中失效的user
       UserGetCommand.flushCache(r.getId());
       return r;
    }

}
