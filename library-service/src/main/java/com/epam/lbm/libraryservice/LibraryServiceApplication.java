package com.epam.lbm.libraryservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableFeignClients
@EnableEurekaClient
public class LibraryServiceApplication {

	public static void main(String[] args) throws NumberFormatException {
		SpringApplication.run(LibraryServiceApplication.class, args);
	}
	
	@Bean
	public RestTemplate getTemplate(){
	   return new RestTemplate();
	}

}
