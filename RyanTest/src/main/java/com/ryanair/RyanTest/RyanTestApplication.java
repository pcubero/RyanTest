package com.ryanair.RyanTest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages={"com.ryanair.RyanTest"})

public class RyanTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(RyanTestApplication.class, args);
	}
}
