package com.moa.cloud.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * Created by Administrator on 2017/11/1.
 */
@RestController
public class DemoConsumerController {

    @Autowired
    private RestTemplate restTemplate;


    @RequestMapping(value="/consumer",method = RequestMethod.GET)
    public String index(){
        System.out.println("consumer.....");
        return restTemplate.getForEntity("http://CLOUD-DEMO-SERVICE/hello",String.class).getBody();
    }

}
