package com.it.basic_provider2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class BasicProvider2Application {

	public static void main(String[] args) {
		SpringApplication.run(BasicProvider2Application.class, args);
	}
}
