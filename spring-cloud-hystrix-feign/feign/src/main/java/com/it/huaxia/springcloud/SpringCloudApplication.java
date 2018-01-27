package com.it.huaxia.springcloud;

import com.it.huaxia.springcloud.zuul.TokenFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.netflix.zuul.filters.discovery.PatternServiceRouteMapper;
import org.springframework.context.annotation.Bean;

/**
 * 重点在feign
 *  其次在zuul:
 *  注意zuul实际上是一个单独的工程，作为一个控件来使用。而且存在多个，不然会有单点故障。
 */
@EnableDiscoveryClient
@SpringBootApplication
@EnableFeignClients
@EnableZuulProxy
public class SpringCloudApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringCloudApplication.class, args);
	}

	@Bean
	public TokenFilter tokenFilter(){
		return new TokenFilter();
	}

	@Bean
	public PatternServiceRouteMapper serviceRouteMapper(){

		// 路径匹配规则，将前面的转换到后面的
		return new PatternServiceRouteMapper("","");
	}




}
