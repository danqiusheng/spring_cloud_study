/*
package com.xk.filter;

*/
/**
 * Created by Administrator on 2017/11/7.
 *//*


import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

*/
/**
 * 用于拦截没有登录的用户，
 * 如果没有登录，则跳回到登录界面，页面存放另一个服务中。 如何跳转
 *//*

@Slf4j
@Component
public class LoginFilter extends ZuulFilter{



    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        RequestContext rc = RequestContext.getCurrentContext();
        HttpServletRequest request = rc.getRequest();
        // 这里判断是否登录... 此时暂时用login来
        Object  obj = request.getParameter("login");
        log.info("在LoginFilter.....");
       if(obj==null){
           // 代表没有登录，则跳转到另一个页面
         //  try {
             //  rc.getResponse().sendRedirect("/index");
           //} catch (IOException e) {
          //     e.printStackTrace();
          // }
       }
        return null;
    }
}
*/
