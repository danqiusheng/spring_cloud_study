package com.moa.cloud.web;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Administrator on 2017/11/1.
 */

@RestController
public class DemoServiceController {


    protected Logger logger =  LoggerFactory.getLogger(getClass());

    @Autowired
    private DiscoveryClient discoveryClient;


    @RequestMapping(value = "/hello",method = RequestMethod.GET)
    public String index(){
        ServiceInstance serviceInstance = discoveryClient.getLocalServiceInstance();
        logger.info("/hello, host:"+serviceInstance.getHost()+";; serviceId:"+serviceInstance.getServiceId());
        return "Hello Demo_Service";
    }


    @RequestMapping("/get")
    public String get(String id){
        System.out.println("id:"+id);
        return "cloud_demo_service";
    }

}
