package com.hryoichi.amateras;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@EnableAsync
@SpringBootApplication
public class AmaterasApplication {

	public static void main(String[] args) {
		SpringApplication.run(AmaterasApplication.class, args);
	}

}
