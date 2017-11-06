package com.moa.cloud.service;

import com.moa.cloud.model.User;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Administrator on 2017/11/6.
 */
public interface UserService {

    // 在feign中绑定参数必须通过value属性来指明具体的参数名
    @RequestMapping(value = "/hello4",method = RequestMethod.GET)
    String hello(@RequestParam("name") String name);

    @RequestMapping(value = "/hello5",method = RequestMethod.GET)
    User hello(@RequestHeader("name")String name, @RequestHeader("age")Integer age);


    @RequestMapping(value = "/hello6",method = RequestMethod.POST)
    String hello(@RequestBody User user);

}
