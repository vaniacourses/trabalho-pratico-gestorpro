package com.gestorpro.auth_service.security;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;

@Service
public class JwtTokenService {

    private static final String SECRET_KEY = "4Z^XrroxR@dWxqf$mTTKwW$!@#qGr4P"; // Chave secreta utilizada para gerar e verificar o token 

    private static final String ISSUER = "pizzurg-api"; // Emissor do token

    public String generateToken(GestorProUserDetails user) {
        try {
            // Define o algoritmo HMAC SHA256 para criar a assinatura do token passando a chave secreta definida
            Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);

            List<String> roles = user.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .toList(); 

            return JWT.create()
                    .withIssuer(ISSUER) // Define o emissor do token
                    .withIssuedAt(creationDate()) // Define a data de emissão do token
                    .withExpiresAt(expirationDate()) // Define a data de expiração do token
                    .withSubject(user.getUsername()) // Define o assunto do token (neste caso, o nome de usuário)
                    .withClaim("roles", roles)
                    .sign(algorithm); // Assina o token usando o algoritmo especificado
        } catch (JWTCreationException exception){
            throw new JWTCreationException("Erro ao gerar token.", exception);
        }
    }

    public String getSubjectFromToken(String token) {
        try {
            // Define o algoritmo HMAC SHA256 para verificar a assinatura do token passando a chave secreta definida
            Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
            return JWT.require(algorithm)
                    .withIssuer(ISSUER) // Define o emissor do token
                    .build()
                    .verify(token) // Verifica a validade do token
                    .getSubject(); // Obtém o assunto (neste caso, o nome de usuário) do token
        } catch (JWTVerificationException exception){
            throw new JWTVerificationException("Token inválido ou expirado.");
        }
    }

    private Instant creationDate() {
        return ZonedDateTime.now(ZoneId.of("America/Recife")).toInstant();
    }

    private Instant expirationDate() {
        return ZonedDateTime.now(ZoneId.of("America/Recife")).plusHours(4).toInstant();
    }

}