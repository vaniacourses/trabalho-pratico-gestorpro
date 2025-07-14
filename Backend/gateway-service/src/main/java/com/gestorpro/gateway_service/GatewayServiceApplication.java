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
	        .route(p -> p
	            .path("/auth/**")
	            .uri("http://localhost:8081"))
			.route(p -> p
				.path("/rh/**")
				.uri("http://localhost:8082"))
			.route(p -> p
				.path("/ti/**")
				.uri("http://localhost:8083"))		
			.route(p -> p
				.path("/financeiro/**")
				.uri("http://localhost:8084"))	
			.route(p -> p
				.path("/gproj/**")
				.uri("http://localhost:8085"))		
			.route(p -> p
				.path("/admin/**")
				.uri("http://localhost:8086"))		
	        .build();
	}
}
