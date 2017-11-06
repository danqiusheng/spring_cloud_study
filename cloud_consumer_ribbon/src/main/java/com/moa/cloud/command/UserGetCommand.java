package com.moa.cloud.command;

import com.moa.cloud.model.User;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixRequestCache;
import com.netflix.hystrix.strategy.concurrency.HystrixConcurrencyStrategy;
import com.netflix.hystrix.strategy.concurrency.HystrixConcurrencyStrategyDefault;
import org.springframework.web.client.RestTemplate;

/**
 * Created by Administrator on 2017/11/3.
 */
public class UserGetCommand extends HystrixCommand<User> {

    private static final HystrixCommandKey GETTER_KEY = HystrixCommandKey.Factory.asKey("CommandKey");

    private RestTemplate restTemplate;
    private String id;

    public UserGetCommand(RestTemplate restTemplate, String id) {
        // 设置组名和命令名
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("GetSetGet")).andCommandKey(GETTER_KEY));
        this.restTemplate = restTemplate;
        this.id = id;
    }

    @Override
    protected User run() throws Exception {
        return restTemplate.getForObject("http://CLOUD-DEMO-SERVICE/getUserById?id={1}",User.class,id);
    }

    @Override
    protected String getCacheKey() {
        return id;
    }


    /**
     * 清除缓存
     * @param id
     */
    public static void flushCache(String id){
        //刷新缓存，根据id进行清理
        HystrixRequestCache.getInstance(GETTER_KEY, HystrixConcurrencyStrategyDefault.getInstance()).clear(id);
    }
}
