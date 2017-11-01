package com.moa.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

// 开启注册中心服务
@EnableEurekaServer
@SpringBootApplication
public class CloudEurekaServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(CloudEurekaServerApplication.class, args);
	}
}
