package com.it.huaxia.basic_consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class BasicConsumerApplication {

	public static void main(String[] args) {
		SpringApplication.run(BasicConsumerApplication.class, args);
	}
}
