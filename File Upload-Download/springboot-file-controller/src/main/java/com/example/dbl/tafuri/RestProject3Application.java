package com.example.dbl.tafuri;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
public class RestProject3Application {

	public static void main(String[] args) {
		System.out.println("----------------------");
		SpringApplication.run(RestProject3Application.class, args);
		System.out.println("====================");
	}

}
