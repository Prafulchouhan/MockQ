package com.server.mock;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class MockQApplication {

	public static void main(String[] args) {
		SpringApplication.run(MockQApplication.class, args);
	}

}
