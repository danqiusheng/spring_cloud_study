package com.moa.cloud.config

import com.netflix.zuul.ZuulFilter
import com.netflix.zuul.context.RequestContext
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import javax.servlet.http.HttpServletResponse

/**
 * Created by Administrator on 2017/11/8.
 */
class PostFilter extends  ZuulFilter {
    Logger log = LoggerFactory.getLogger(getClass())
    @Override
    String filterType() {
        return "post"
    }

    @Override
    int filterOrder() {
        return 2000
    }

    @Override
    boolean shouldFilter() {
        return true
    }

    @Override
    Object run() {
        log.info("this is a post filter: Recive Message")
        HttpServletResponse response = RequestContext.getCurrentContext().getResponse()
        response.getOutputStream().print("test out OK")
        response.flushBuffer()
    }
}
