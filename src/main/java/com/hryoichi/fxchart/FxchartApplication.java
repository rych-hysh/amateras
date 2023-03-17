package com.hryoichi.fxchart;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class FxchartApplication {

	public static void main(String[] args) {
		SpringApplication.run(FxchartApplication.class, args);
	}

}
