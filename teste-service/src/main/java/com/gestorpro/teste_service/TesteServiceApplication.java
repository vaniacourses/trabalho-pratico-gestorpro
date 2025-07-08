package com.gestorpro.teste_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = {
    "com.gestorpro.teste_service", 
    "com.gestorpro.jwt_utils"
})
@SpringBootApplication
public class TesteServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(TesteServiceApplication.class, args);
	}

}
