package com.moa.cloud.service;

import com.moa.cloud.model.User;
import org.springframework.stereotype.Component;

/**
 * Created by Administrator on 2017/11/6.
 */
@Component
public class UserServiceFallBack implements UserService {
    @Override
    public String index() {
        return "xxxxx";
    }

    @Override
    public String hello(String name) {
        return null;
    }

    @Override
    public User hello(String name, Integer age) {
        return null;
    }

    @Override
    public String hello(User user) {
        return null;
    }
}
