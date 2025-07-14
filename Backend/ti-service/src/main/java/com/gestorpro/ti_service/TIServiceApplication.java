package com.gestorpro.ti_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.gestorpro.ti_service", "com.gestorpro.jwt_utils"})
public class TIServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(TIServiceApplication.class, args);
    }
}