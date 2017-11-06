package com.moa.cloud.service;

import com.moa.cloud.command.*;
import com.moa.cloud.model.User;
import com.netflix.hystrix.HystrixEventType;
import com.netflix.hystrix.HystrixRequestLog;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCollapser;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheKey;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheRemove;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheResult;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import com.netflix.ribbon.proxy.annotation.Http;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Created by Administrator on 2017/11/3.
 */
@Service
@Slf4j
public class UserService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private UserService userService;

    public void test(String id){
        log.info("test;;;id :{}",id);
       User user1 =   getUserByAnnotation(id);
       User user2 =  getUserByAnnotation(id);
       User user3 =  getUserByAnnotation(id+"1");
    }


    @CacheResult(cacheKeyMethod = "getCacheKey") //缓存结果
    @HystrixCommand  //　开启hystrix的新特性
    // 如果不定义缓存主键的生成策略，则默认使用方法上面的所有参数作为key缓存
    public User getUserByAnnotation(@CacheKey("id") String id){
        return restTemplate.getForObject("http://CLOUD-DEMO-SERVICE/getUserById?id={1}",User.class,id);
    }


    /**
     *CacheRemove注解中的commandKey必须指定
     * 值为需要使用请求缓存的请求命令
     * 需要测试....
     * @param user
     */
    @CacheRemove(commandKey = "getUserById")
    @HystrixCommand
    public void update(User user){
        restTemplate.postForObject("http://CLOUD-DEMO-SERVICE/users",user,User.class);
    }


    public String getCacheKey(String id){
        log.info("id:{}",id);
        return id;
    }


    /**
     * 查询单个
     * @param id
     * @return
     */
    public User find(String id){
        return restTemplate.getForObject("http://CLOUD-DEMO-SERVICE/users/{1}",User.class,id);
    }

    /**
     * 查询多个
     * @param ids
     * @return
     */
    @HystrixCommand(fallbackMethod = "fallBack")
    public List<User> findAll(List<String> ids){
        // TODO: 以下代码使用于一个对象中有一个List集合
       // ParameterizedTypeReference<List<User>> typeRef = new ParameterizedTypeReference<List<User>>() {};
       // ResponseEntity<List<User>> responseEntity = restTemplate.exchange("http://CLOUD-DEMO-SERVICE/users?ids={1}", HttpMethod.POST, null, typeRef,StringUtils.join(ids,","));
       //  List<User> myModelClasses = responseEntity.getBody();
        // TODO: 当返回的是集合
        return  Arrays.asList(restTemplate.getForObject("http://CLOUD-DEMO-SERVICE/users?ids={1}",User[].class, StringUtils.join(ids,",")));
    }

    public List<User> fallBack(){
        return new ArrayList<User>();
    }

    /**
     * 通过id获取用户
     *  测试缓存
     * @param id 主键
     * @return User
     */
    public User getUserById(String id) {
        //初始化context
         HystrixRequestContext context =  HystrixRequestContext.getContextForCurrentThread();
         if(context==null){
             System.out.println("xxxxx");
            context = HystrixRequestContext.initializeContext();
         }
        //两个一样的command
        System.out.println("第一个查询.......");
        UserCommand command2a =   new UserCommand(restTemplate,id);
        // 可以从userCommand里面调用isResponseFromCache 可以得知该数据是从缓存中获取
        User one = command2a.execute();
        System.out.println(command2a.isResponseFromCache());

        System.out.println("第二个查询.......");
        UserCommand two = new UserCommand(restTemplate,id);
        two.execute();
        System.out.println(two.isResponseFromCache());

        System.out.println("第三个查询.......");
        UserCommand three = new UserCommand(restTemplate,id+"1");
        User  user = three.execute();
        System.out.println(three.isResponseFromCache());

        // 测试是否开启缓存
        return one;
    }


    public void clearCache() {
        //初始化context
        HystrixRequestContext context =  HystrixRequestContext.getContextForCurrentThread();
        if(context==null){
            context = HystrixRequestContext.initializeContext();
        }

        UserGetCommand userGetCommand =  new  UserGetCommand(restTemplate,"15");
        userGetCommand.execute();
        System.out.println(userGetCommand.isResponseFromCache());

        User user = new User("15","李四");
        UserPostCommand userPostCommand = new UserPostCommand(restTemplate,user);
        // 执行保存
        userPostCommand.execute();
    }

    //  请求合并
    public User batchUser(String id) throws ExecutionException, InterruptedException {
        //初始化context
        HystrixRequestContext context =  HystrixRequestContext.getContextForCurrentThread();
        if(context==null){
            context = HystrixRequestContext.initializeContext();
        }
        Future<User> f1 = new UserCollapseCommand(userService,id).queue();
        return f1.get();
    }


    // 测试合并
    public void testBatchUser() throws ExecutionException, InterruptedException {
        //初始化request
        HystrixRequestContext context = HystrixRequestContext.initializeContext();
        //直接调用collapser
        Future<User> f1 = new UserCollapseCommand(userService,""+1).queue();
        Future<User> f2 = new UserCollapseCommand(userService,""+2).queue();
        Future<User> f3 = new UserCollapseCommand(userService,""+3).queue();
        Future<User> f4 = new UserCollapseCommand(userService,""+4).queue();

       // System.out.println(   new UserCollapseCommand(userService,"1").execute());
        //System.out.println(   new UserCollapseCommand(userService,"2").execute());
        System.out.println( f1.get());
        System.out.println( f2.get());
        System.out.println( f3.get());
        System.out.println( f4.get());

        //只调用了一次command
        // assert that the batch command 'GetValueForKey' was in fact
        // executed and that it executed only once
        System.out.println( HystrixRequestLog.getCurrentRequest().getExecutedCommands().size());
        com.netflix.hystrix.HystrixCommand<?> command = HystrixRequestLog.getCurrentRequest().getExecutedCommands().toArray(new com.netflix.hystrix.HystrixCommand<?>[1])[0];
        System.out.println(command);

        // assert the command is the one we're expecting
        System.out.println( command.getCommandKey().name());
        // confirm that it was a COLLAPSED command execution
        System.out.println(command.getExecutionEvents().contains(HystrixEventType.COLLAPSED));
        // and that it was successful
        System.out.println(command.getExecutionEvents().contains(HystrixEventType.SUCCESS));

    }


    /**
     * 测试注解进行合并请求
     * @param id
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public void testBatchByAnotation(String id) throws ExecutionException, InterruptedException {
        HystrixRequestContext context = HystrixRequestContext.initializeContext();
        Future<User> f1 = userService.findByAnotation("1");
        Future<User> f2 = userService.findByAnotation("2");
        Future<User> f3 = userService.findByAnotation("3");
        User b1 = f1.get();
        User b2 = f2.get();
        User b3 = f3.get();
        Thread.sleep(3000);
        Future<User> f4 = userService.findByAnotation("12");
        User b4 = f4.get();
        System.out.println("b1>>>"+b1.getId());
        System.out.println("b2>>>"+b2.getId());
        System.out.println("b3>>>"+b3.getId());
        System.out.println("b4>>>"+b4.getId());
        context.close();
    }

    @HystrixCollapser(batchMethod = "findAllByAnotation",collapserProperties = {@HystrixProperty(name ="timerDelayInMilliseconds",value = "100")})
    public Future<User> findByAnotation(String id){
            return null;
    }


    @HystrixCommand(fallbackMethod = "fallBack")
    public List<User> findAllByAnotation(List<String> ids){
        return  Arrays.asList(restTemplate.getForObject("http://CLOUD-DEMO-SERVICE/users?ids={1}",User[].class, StringUtils.join(ids,",")));
    }


    public List<User> fallBack(List<String> ids){
        return new ArrayList<User>();
    }

}
