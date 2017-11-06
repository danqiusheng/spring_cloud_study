package com.moa.cloud.service;

import com.moa.cloud.model.User;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Administrator on 2017/11/6.
 */
// 指定服务名绑定服务
@FeignClient(value = "cloud-demo-service",fallback = UserServiceFallBack.class)
public interface UserService {

    @RequestMapping("/hello")
    public String index();


    // 在feign中绑定参数必须通过value属性来指明具体的参数名
    @RequestMapping(value = "/hello1",method = RequestMethod.GET)
    String hello(@RequestParam("name") String name);

    @RequestMapping(value = "/hello2",method = RequestMethod.GET)
    User hello(@RequestHeader("name")String name, @RequestHeader("age")Integer age);


    @RequestMapping(value = "/hello3",method = RequestMethod.POST)
    String hello(@RequestBody User user);
}
