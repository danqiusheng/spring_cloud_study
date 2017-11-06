package com.moa.cloud.command;

import com.moa.cloud.model.User;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import org.springframework.web.client.RestTemplate;

/**
 * Created by Administrator on 2017/11/3.
 *  缓存：
 *  1. 测试的时候需要初始化
 *    HystrixRequestContext context =  HystrixRequestContext.getContextForCurrentThread();
 *  2. 默认分配线程池10个线程，十次请求以上开始请求缓存
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
