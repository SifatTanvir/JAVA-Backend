package com.dbl.nsl.erp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class HrmErpDblApplication {

	public static void main(String[] args) {
		SpringApplication.run(HrmErpDblApplication.class, args);
		System.out.println("Welcome to Tafuri Human Resource Management Software");
	}
}
