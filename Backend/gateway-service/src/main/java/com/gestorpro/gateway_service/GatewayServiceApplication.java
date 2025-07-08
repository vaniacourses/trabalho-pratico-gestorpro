package com.gestorpro.gateway_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GatewayServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayServiceApplication.class, args);
	}

	@Bean
	public RouteLocator myRoutes(RouteLocatorBuilder builder) {
		return builder.routes()
				.route("auth_service_route", p -> p // Adicionar um ID para a rota é uma boa prática
						.path("/auth/**")
						.uri("http://localhost:8081"))
				.route("admin_service_route", p -> p // Supondo que este seja um serviço de admin
						.path("/admin/**")
						.uri("http://localhost:8082"))

				.route("ti_service_route", p -> p
						.path("/ti/**")
						.uri("http://localhost:8083"))
				.build();
	}
}