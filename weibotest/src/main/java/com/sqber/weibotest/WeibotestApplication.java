package com.sqber.weibotest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class WeibotestApplication {

	public static void main(String[] args) {
		SpringApplication.run(WeibotestApplication.class, args);
	}

}
