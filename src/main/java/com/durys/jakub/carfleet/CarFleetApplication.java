package com.durys.jakub.carfleet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
@Configuration
public class CarFleetApplication {

	public static void main(String[] args) {
		SpringApplication.run(CarFleetApplication.class, args);
	}

}
