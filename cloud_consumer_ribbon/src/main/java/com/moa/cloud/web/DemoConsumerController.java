package com.moa.cloud.web;

import com.moa.cloud.service.ConsumerService;
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
   private ConsumerService consumerService;


    @RequestMapping(value="/consumer",method = RequestMethod.GET)
    public String index(){
        return consumerService.index();
    }

    @RequestMapping(value="/get",method = RequestMethod.GET)
    public String get(){
        return consumerService.get();
    }
}
