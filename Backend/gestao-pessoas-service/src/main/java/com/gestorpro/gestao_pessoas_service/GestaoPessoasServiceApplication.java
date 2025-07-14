package com.gestorpro.gestao_pessoas_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@ComponentScan(basePackages = {"com.gestorpro.gestao_pessoas_service", "com.gestorpro.jwt_utils"})
public class GestaoPessoasServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(GestaoPessoasServiceApplication.class, args);
	}

}