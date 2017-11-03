package com.moa.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;


@SpringBootApplication
// 启用仪表盘
@EnableHystrixDashboard
public class CloudHystrixDashboardApplication {

	public static void main(String[] args) {
		SpringApplication.run(CloudHystrixDashboardApplication.class, args);
	}
}
