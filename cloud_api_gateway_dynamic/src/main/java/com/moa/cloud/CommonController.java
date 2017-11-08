package com.moa.cloud;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Administrator on 2017/11/8.
 */
@Controller
@RequestMapping("/base")
public class CommonController {

    @Value("${application.cname}")
    private String cname;

    @RequestMapping("/get")
    public String get(){
        return this.cname;
    }
}
