package com.gestorpro.auth_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gestorpro.auth_service.dto.CreateUserDto;
import com.gestorpro.auth_service.dto.LoginUserDto;
import com.gestorpro.auth_service.dto.RecoveryJwtTokenDto;
import com.gestorpro.auth_service.service.UserService;

@CrossOrigin(origins = "http://localhost:5174")
@RestController
@RequestMapping("/auth")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<RecoveryJwtTokenDto> authenticateUser(@RequestBody LoginUserDto loginUserDto) {
        RecoveryJwtTokenDto token = userService.authenticateUser(loginUserDto);
        return new ResponseEntity<>(token, HttpStatus.OK);
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('ROLE_RH')")
    public ResponseEntity<String> createUser(@RequestBody CreateUserDto createUserDto) {
        userService.createUser(createUserDto);
        return ResponseEntity.ok("Usuário criado.");
    }
}