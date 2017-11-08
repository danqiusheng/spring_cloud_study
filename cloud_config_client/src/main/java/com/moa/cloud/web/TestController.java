package com.moa.cloud.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Administrator on 2017/11/7.
 */

//这个注解不明白
@RefreshScope
@RestController
public class TestController {

    private String name;

    @Autowired
    private Environment environment;

    @RequestMapping("/from")
    public String from(){
        return environment.getProperty("from","default");
    }
}
