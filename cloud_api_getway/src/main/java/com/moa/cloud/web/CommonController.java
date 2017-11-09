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
public class CommonController {


    @RequestMapping("/base/toIndex")
    public String toIndex(){
        return "index";
    }



}
