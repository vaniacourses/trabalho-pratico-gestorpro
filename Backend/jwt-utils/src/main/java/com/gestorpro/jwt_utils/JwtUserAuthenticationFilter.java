package com.gestorpro.jwt_utils;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// @Component
public class JwtUserAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenService jwtTokenService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // Verifica se o endpoint requer autenticação antes de processar a requisição
        String path = request.getRequestURI();

        if (path.startsWith("/public/")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = recoveryToken(request); // Recupera o token do cabeçalho Authorization da requisição
        if (token != null) {
            String subject = jwtTokenService.getSubjectFromToken(token);
            List<String> roles = jwtTokenService.getRolesFromToken(token);
            // Cria um objeto de autenticação do Spring Security
            Authentication authentication =
                    new UsernamePasswordAuthenticationToken(subject, null, convertRoles2GrantedAuthority(roles));
            // Define o objeto de autenticação no contexto de segurança do Spring Security
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401
    	    response.setContentType("application/json");
    	    response.getWriter().write("{\"error\": \"Token ausente ou inválido.\"}");
	    return;
        }
        
        // O 'else' que lançava a exceção foi REMOVIDO.
        // Agora, a requisição sempre continua para o próximo filtro. A decisão de
        // bloquear a rota (se for privada e não houver autenticação) será do Spring Security.
        filterChain.doFilter(request, response);
    }

    // Recupera o token do cabeçalho Authorization da requisição
    private String recoveryToken(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null) {
            return authorizationHeader.replace("Bearer ", "");
        }
        return null;
    }

    private Collection<? extends GrantedAuthority> convertRoles2GrantedAuthority(List<String> roles) {
        return roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

}
