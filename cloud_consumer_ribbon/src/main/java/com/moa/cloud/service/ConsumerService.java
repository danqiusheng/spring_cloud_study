package com.moa.cloud.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Created by Administrator on 2017/11/2.
 */
@Service
public class ConsumerService {
    @Autowired
    private RestTemplate restTemplate;


    // 加入此注解
    @HystrixCommand(fallbackMethod = "consumerFallback")
    public String index() {
        System.out.println("consumer.....consumer()...");
        return restTemplate.getForEntity("http://CLOUD-DEMO-SERVICE/hello", String.class).getBody();
    }


    private String consumerFallback() {
        return "consumerFallback";
    }

    @HystrixCommand(fallbackMethod = "consumerFallback")
    public String get() {
        System.out.println("consumer.....get()...");
        return restTemplate.getForEntity("http://CLOUD-DEMO-SERVICE/get?id={1}", String.class, "123").getBody();
    }
}
