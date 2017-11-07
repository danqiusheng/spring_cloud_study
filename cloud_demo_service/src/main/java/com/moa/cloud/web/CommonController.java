package com.moa.cloud.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Administrator on 2017/11/7.
 */
@Controller
@RequestMapping("/base")
public class CommonController {


    @RequestMapping("/toIndex")
    public String toIndex(){
        return "index";
    }

}
