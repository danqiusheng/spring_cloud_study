package com.moa.cloud.service;

import com.moa.cloud.command.UserCommand;
import com.moa.cloud.model.User;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Created by Administrator on 2017/11/3.
 */
@Service
public class UserService {

    @Autowired
    private RestTemplate restTemplate;


    private static int count ;



    /**
     * 通过id获取用户
     *  测试缓存
     * @param id 主键
     * @return User
     */
    public User getUserById(String id) {
        //初始化context
         HystrixRequestContext context =  HystrixRequestContext.getContextForCurrentThread();
         if(context==null){
             System.out.println("xxxxx");
            context = HystrixRequestContext.initializeContext();
         }
        //两个一样的command
        System.out.println("第一个查询.......");
        UserCommand command2a =   new UserCommand(restTemplate,id);
        // 可以从userCommand里面调用isResponseFromCache 可以得知该数据是从缓存中获取
        User one = command2a.execute();
        System.out.println(command2a.isResponseFromCache());

        System.out.println("第二个查询.......");
        UserCommand two = new UserCommand(restTemplate,id);
        two.execute();
        System.out.println(two.isResponseFromCache());

        System.out.println("第三个查询.......");
        UserCommand three = new UserCommand(restTemplate,id+"1");
        User  user = three.execute();
        System.out.println(three.isResponseFromCache());

        // 测试是否开启缓存
        return one;
    }


}
