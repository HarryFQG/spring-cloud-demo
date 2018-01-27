package com.it.huaxia.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableDiscoveryClient
@EnableCircuitBreaker
@EnableHystrixDashboard
public class SpringCloudApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringCloudApplication.class, args);
	}

	/**
	 * 注入随机算法，
	 * 如果想实现自己的算法，则实现IRule接口，再注入即可
	 * @return

	 @Bean
	 public IRule ribbonRule(){
	 return new RandomRule();
	 }
	 */


	//-------------------------------
	/**
	 * 申明一个负载均衡的请求器
	 * RestTemplate:不在是面向IP调用的，而是面向服务调用的
	 */
	@Bean
	@LoadBalanced
	RestTemplate restTemplate(){

		return new RestTemplate();
	}



}
