package com.moa.cloud.listener;

import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Created by Administrator on 2017/11/3.
 */
@Component
public class HystrixCommand implements CommandLineRunner{
    @Override
    public void run(String... args) throws Exception {
        //初始化context
        HystrixRequestContext context = HystrixRequestContext.initializeContext();
    }
}
