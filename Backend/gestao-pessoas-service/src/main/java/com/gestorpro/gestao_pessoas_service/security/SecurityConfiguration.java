/*package com.gestorpro.gestao_pessoas_service.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.gestorpro.jwt_utils.JwtUserAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfiguration {

    @Autowired
    private JwtUserAuthenticationFilter jwtUserAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                .anyRequest().authenticated()
            )
            .addFilterBefore(jwtUserAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
            .build();
    }
}*/

package com.gestorpro.gestao_pessoas_service.security;

import com.gestorpro.jwt_utils.JwtUserAuthenticationFilter;
import com.gestorpro.jwt_utils.JwtTokenService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfiguration {

    // Este método cria o bean do serviço de token para que possa ser injetado no filtro
    @Bean
    public JwtTokenService jwtTokenService() {
        return new JwtTokenService();
    }

    // Este método cria o bean do filtro, já que removemos o @Component
    @Bean
    public JwtUserAuthenticationFilter jwtUserAuthenticationFilter() {
        return new JwtUserAuthenticationFilter();
    }

    // Usamos uma única e mais simples cadeia de segurança
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                // Regra 1: Permite acesso PÚBLICO ao endpoint de funcionários.
                .requestMatchers("/api/funcionarios/**").permitAll()
                // Regra 2: Exige autenticação para TODAS as outras requisições.
                .anyRequest().authenticated()
            )
            // Adiciona o filtro. Como o filtro foi corrigido para não lançar mais erro,
            // ele apenas tentará autenticar se houver um token, e deixará o Spring Security
            // barrar as rotas privadas se não houver autenticação.
            .addFilterBefore(jwtUserAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        
        return http.build();
    }
}
