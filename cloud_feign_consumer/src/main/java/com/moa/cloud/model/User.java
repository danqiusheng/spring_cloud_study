package com.moa.cloud.model;

/**
 * Created by Administrator on 2017/11/6.
 */
public class User {
    private String id;
    private String name;

    private Integer age;


    public User(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public User(String name, Integer age){
        this.name = name;
        this.age = age;
    }

    public User() {
    }

    public String getId() {
        return id;
    }

    public User setId(String id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public User setName(String name) {
        this.name = name;
        return this;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

}
