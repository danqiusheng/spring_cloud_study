package com.moa.cloud.config

import com.netflix.zuul.ZuulFilter
import com.netflix.zuul.context.RequestContext
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import javax.servlet.http.HttpServletRequest

/**
 * Created by Administrator on 2017/11/8.
 */

class  PreFilter extends  ZuulFilter{
    Logger log = LoggerFactory.getLogger(PreFilter.class)



    @Override
    boolean shouldFilter(){
        return true
    }

    @Override
    Object run(){
        RequestContext ctx = RequestContext.getCurrentContext()
        HttpServletRequest request = ctx.getRequest()
        log.info("this is a pre filter {} to {}",request.getMethod(),request.getRequestURL().toString())
    }

    @Override
    String filterType() {
        return "pre"
    }

    @Override
    int filterOrder() {
        return 1000
    }
}