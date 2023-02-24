package com.example.demo.security.config;

import jakarta.servlet.Filter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;

@Configuration // indica que esta clase es de configuración de Spring
@EnableWebSecurity // habilita la seguridad web de Spring
@RequiredArgsConstructor // genera un constructor con los campos marcados como final
public class SecurityConfiguration {

    @Autowired
    private  JwtAuthenticationFilter jwtAuthFilter; // inyecta una instancia de JwtAuthenticationFilter
    @Autowired
    private  AuthenticationProvider authenticationProvider; // inyecta una instancia de AuthenticationProvider
    @Autowired
    private  LogoutHandler logoutHandler; // inyecta una instancia de LogoutHandler

    @Bean // indica que este método devuelve un objeto que debe ser registrado como bean de Spring
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http // configura el objeto HttpSecurity que se le pasa como argumento

                .csrf()
                .disable() // deshabilita la protección CSRF

                .authorizeHttpRequests()
                .requestMatchers("/api/v1/auth/**")
                .permitAll() // permite el acceso sin autenticación a todas las peticiones que empiezan con /api/v1/auth

                .anyRequest()
                .authenticated() // requiere autenticación para todas las demás peticiones

                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // configura el uso de sesiones sin estado

                .and()
                .authenticationProvider(authenticationProvider) // configura el proveedor de autenticación

                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class) // añade el filtro JwtAuthenticationFilter antes del filtro UsernamePasswordAuthenticationFilter

                .logout()
                .logoutUrl("/api/v1/auth/logout") // configura la URL de logout
                .addLogoutHandler(logoutHandler) // añade el logout handler
                .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext()) // configura el handler de éxito de logout

        ;

        return http.build(); // devuelve el objeto SecurityFilterChain construido
    }
}
