package com.moa.cloud.web;

import com.moa.cloud.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Administrator on 2017/11/1.
 */
@RestController
@Slf4j
public class DemoServiceController {


    @Autowired
    private DiscoveryClient discoveryClient;


    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String index() {
        ServiceInstance serviceInstance = discoveryClient.getLocalServiceInstance();
        log.info("/hello, host:{};serviceId:{}" ,serviceInstance.getHost() , serviceInstance.getServiceId());
        return "Hello Demo_Service";
    }


    @RequestMapping("/get")
    public String get(String id) throws InterruptedException {
        Thread.sleep(5000L);
        ServiceInstance serviceInstance = discoveryClient.getLocalServiceInstance();
        log.info("/hello, host:{};serviceId:{}" ,serviceInstance.getHost() , serviceInstance.getServiceId());
        // 加入延时,触发断路器

        return "cloud_demo_service";
    }


    @RequestMapping("/getUserById")
    public User getUserById(String id) {
        log.info("search user..id:" + id + "...........");
        User user = new User(id, "李四" + id);
        log.info(" search over .....");
        return user;
    }


    @RequestMapping(value = "/users", method = RequestMethod.POST)
    public User save(@RequestBody User user) {
        log.info("在serviceController....id:{}:name:{}",user.getId(),user.getName());
        return new User(user.getId(),user.getName());
    }



    @RequestMapping(value = "/users/", method = RequestMethod.GET)
    public User getById(@PathVariable String id) {
        return new User(id,"xx");
    }
    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public List<User> getByIds(String[] ids){
        List<User> users = new ArrayList<User>();
            for (String id : ids){
                    User user = new User(id,"xx"+id);
                    users.add(user);
            }
        System.out.println(users);
            return users;
    }





}
