package com.moa.cloud.web;

import com.moa.cloud.command.UserCollapseCommand;
import com.moa.cloud.command.UserCommand;
import com.moa.cloud.model.User;
import com.moa.cloud.service.ConsumerService;
import com.moa.cloud.service.UserService;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixEventType;
import com.netflix.hystrix.HystrixRequestLog;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Created by Administrator on 2017/11/1.
 */
@RestController
public class DemoConsumerController {

    @Autowired
    private ConsumerService consumerService;


    @Autowired
    private UserService userService;

    /**
     * 测试消费其他的服务
     * @return
     */
    @RequestMapping(value = "/consumer", method = RequestMethod.GET)
    public String index() {
        return consumerService.index();
    }


    /**
     * 测试获取
     * @return
     */
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public String get() {
        return consumerService.get();
    }


    /**
     * 测试缓存
     * @return
     */
    @RequestMapping(value = "/getUserById", method = RequestMethod.GET)
    public User getUserById() {
        return userService.getUserById("2");
    }


    /**
     * 测试清除缓存
     * @return
     */
    @RequestMapping(value = "/testCacheClear", method = RequestMethod.GET)
    public String testCacheClear() {
        userService.clearCache();
        return "clear OK";
    }



    @RequestMapping("/getUserByAnnotation")
    public String getUserByAnnotation(String id){
        //初始化context
       HystrixRequestContext context =  HystrixRequestContext.getContextForCurrentThread();
        if(context==null){
            context = HystrixRequestContext.initializeContext();
        }
        userService.getUserByAnnotation(id);
        return "test annotation cache success";
    }


    @RequestMapping("/getBatchUser")
    public User getBatchUser(String id) throws ExecutionException, InterruptedException {
       // TODO:  代码测试合并效果
         userService.testBatchUser();
        // TODO: 请求合并
       //  return  userService.batchUser(id);

        return new User();
    }

    @RequestMapping("/getBatchUserByAnotation")
    public User getBatchUserByAnotation() throws ExecutionException, InterruptedException {
        // TODO: 注解请求合并
        userService.testBatchByAnotation("1");
        return new User();
    }
}

