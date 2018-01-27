package com.it.huaxia.basic_consumer2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class BasicConsumer2Application {

	public static void main(String[] args) {
		SpringApplication.run(BasicConsumer2Application.class, args);
	}
}
