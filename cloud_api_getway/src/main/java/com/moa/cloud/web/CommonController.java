package com.moa.cloud.web;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Administrator on 2017/11/7.
 */
@Controller
@RequestMapping("/base")
@RefreshScope
public class CommonController {


    @RequestMapping("/toIndex")
    public String toIndex(){
        return "index";
    }
    @Value("${application.cname}")
    private String cname;

    @RequestMapping("/get")
    @ResponseBody
    public String get(){
        return this.cname;
    }
}
