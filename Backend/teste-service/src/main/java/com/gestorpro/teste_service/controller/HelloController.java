package com.gestorpro.teste_service.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;



@RestController
public class HelloController {
    @GetMapping({"/", "/home"})
    public String getHello() {
        return "Hello, World!";
    }    
}
