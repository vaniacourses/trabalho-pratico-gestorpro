package com.gestorpro.financeiro_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@ComponentScan(basePackages = {"com.gestorpro.financeiro_service", "com.gestorpro.jwt_utils"})
public class FinanceiroServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(FinanceiroServiceApplication.class, args);
	}
}
