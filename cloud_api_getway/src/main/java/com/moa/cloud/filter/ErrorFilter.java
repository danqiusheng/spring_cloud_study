package com.moa.cloud.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.post.SendErrorFilter;
import org.springframework.stereotype.Component;

/**
 * Created by Administrator on 2017/11/7.
 */
@Component
@Slf4j
public class ErrorFilter extends ZuulFilter {

    @Autowired
    private SendErrorFilter sendErrorFilter;

    public String filterType() {
        return "error";
    }

    //TODO: 级别比转发的过滤器高一点点,用于设定错误页面
    @Override
    public int filterOrder() {
        return -1;
    }

    @Override
    public boolean shouldFilter() {
        return  true;
    }

    @Override
    public Object run() {
        RequestContext  ctx = RequestContext.getCurrentContext();
        Throwable throwable = ctx.getThrowable();
        // TODO: 设置跳转到错误的页面 采用的转发(如果是异步请求出错... 跳转页面有问题? )
        sendErrorFilter.setErrorPath("/base/toIndex");
        // TODO: 自定义错误信息到错误页面显示
        ctx.getRequest().setAttribute("msg",throwable.getCause().getMessage());
        log.error("this is a ErrorFilter: {}",throwable.getCause().getMessage());
        return null;
    }
}
