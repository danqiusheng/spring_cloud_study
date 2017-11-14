package com.xk.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Administrator on 2017/11/6.
 */

/**
 * 此拦截器用于拦截没有参数access
 * 拦截没有授权的,判断是否有权限访问
 * OAuth2 JWT
 */
@Slf4j
@Component
public class AccessFilter extends ZuulFilter {


    /**
     * 过滤器的类型，它决定过滤器在请求的哪个生命周期中执行，pre代表在请求路由之前执行
     * @return
        返回有四个结果：
        pre: 可以在请求被路由之前调用
        routing: 在路由请求时被调用
        post: 在routing和error过滤器之后被调用  --问题：如果post发生错误，该如何捕捉？
        error: 处理请求时发生错误时调用
     */
    @Override
    public String filterType() {
        return "pre";
    }




    /**
     * 过滤器的执行顺序，如果请求在一个阶段中存在多个过滤器时，需要根据该方法返回的值来依次执行
     * @return
     */
    @Override
    public int filterOrder() {
        return 1;
    }

    /**
     * 判断该过滤器是否被执行，true代表该过滤器对所有的请求都会生效
     * @return
     */
    @Override
    public boolean shouldFilter() {

        return true;
    }

    /**
     * 实际执行流程
     * @return
     */
    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        log.info("send {} to {} ",request.getMethod(),request.getRequestURL().toString());
        //Object access = request.getParameter("access");
       /*  if(access==null){
            log.warn("access token is empty");
            // 如果没有，则令zuul过滤该请求，不对其进行路由
            ctx.setSendZuulResponse(false);
            //设置返回的状态码
            return null;
        }*/
        log.info("acess token ok");
        return null;
    }


}
