package com.moa.cloud.web;

import com.moa.cloud.model.User;
import com.moa.cloud.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Administrator on 2017/11/6.
 */
@RestController
public class UserController {


    @Autowired
    private UserService userService;


    @RequestMapping(value = "/feign-consumer",method = RequestMethod.GET)
    public String index(){
        System.out.println("xxx");
        return userService.index();
    }


    @RequestMapping(value = "/test",method = RequestMethod.GET)
    public String test(){
        System.out.println("test OK ");
        return "test OK";
    }

    @RequestMapping(value = "/feign-consumer2",method =RequestMethod.GET )
    public String feignIndex(){
        StringBuilder sb = new StringBuilder();
        sb.append(userService.index()).append("\n");
        sb.append(userService.hello("张三")).append("\n");
        sb.append(userService.hello("张三",30)).append("\n");
        sb.append(userService.hello(new User("张三",20))).append("\n");
        return sb.toString();
    }
}
