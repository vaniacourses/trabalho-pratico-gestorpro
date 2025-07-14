package com.gestorpro.gateway_service.utils;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Predicate;

@Component
public class RouterValidator {

    public static final List<String> openApiEndpoints = List.of(
            "/auth/login",
            "/auth/register" // outras rotas públicas
    );

    public Predicate<ServerHttpRequest> isSecured = request -> 
        openApiEndpoints.stream()
                        .noneMatch(uri -> request.getURI().getPath().contains(uri));
}