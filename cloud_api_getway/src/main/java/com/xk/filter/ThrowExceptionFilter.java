package com.xk.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Created by Administrator on 2017/11/7.
 */
@Component
@Slf4j
public class ThrowExceptionFilter extends ZuulFilter {



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

    /**
     * 发生错误执行
     * @return
     */
    @Override
    public Object run() {
      log.info("this is a filter that will happen some error.......");
        RequestContext ctx = RequestContext.getCurrentContext();

          doSomethig();

      return null;


    }

    private void doSomethig() {
      // 测试抛出异常
      //   throw new RuntimeException("some errors......");
    }
}
