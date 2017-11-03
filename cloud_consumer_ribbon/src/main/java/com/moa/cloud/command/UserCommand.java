package com.moa.cloud.command;

import com.moa.cloud.model.User;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixThreadPoolKey;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import org.springframework.web.client.RestTemplate;

/**
 * Created by Administrator on 2017/11/3.
 */
public class UserCommand extends HystrixCommand<User> {

    private RestTemplate restTemplate;

    private String id;

    public UserCommand(RestTemplate restTemplate, String id) {

        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("UserGroup")));
        this.restTemplate = restTemplate;
        this.id = id;
    }

    /**
     * 获取缓存key
     *
     * @return
     */
    @Override
    protected String getCacheKey() {
        return id;
    }

    @Override
    protected User run() throws Exception {
        return restTemplate.getForObject("http://CLOUD-DEMO-SERVICE/getUserById?id={1}",User.class,id);
    }

    @Override
    protected User getFallback() {
        System.out.println("getFallback");
        return new User();
    }
}
