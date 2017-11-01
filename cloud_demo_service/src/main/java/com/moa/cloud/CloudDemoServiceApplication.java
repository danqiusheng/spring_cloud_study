package com.moa.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;



// 创建Eureka接口针对Eureka客户端
@EnableEurekaClient
@SpringBootApplication
public class CloudDemoServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CloudDemoServiceApplication.class, args);
	}
}
