package com.example.demo.security.config;

import com.example.demo.security.token.TokenRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Security;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
//indica que esta clase es un componente de Spring, lo que significa que Spring manejará su ciclo de vida y puede inyectar otras dependencias en ella.
@RequiredArgsConstructor //es de Lombok y genera un constructor que toma todos los campos marcados con @NonNull o final y los inicializa.
public class    JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private  JwtService jwtService;
    @Autowired
    private  UserDetailsService userDetailsService;
    @Autowired
    private  TokenRepository tokenRepository;

    // Realiza la autenticación del usuario en cada petición utilizando el token JWT proporcionado en el encabezado "Authorization"
    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException { //lanza una excepción si ocurre un error de E/S
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;

        // Verifica que el encabezado "Authorization" comience con la cadena "Bearer" y continúa si es así, de lo contrario, continúa la cadena de filtros.
        if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // Extrae el token JWT del encabezado de autorización
        jwt = authHeader.substring(7);

        // Extrae el correo electrónico del usuario del token JWT
        userEmail = jwtService.extractUsername(jwt);

        // Verifica si ya hay una autenticación en contexto y si no, autentica al usuario
        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // Carga los detalles del usuario a partir del correo electrónico
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);

            // Verifica si el token es válido y no ha sido revocado o caducado
            var isTokenValid = tokenRepository.findByToken(jwt)
                    .map(t -> !t.isExpired() && !t.isRevoked())
                    .orElse(false);

            // Si el token es válido, crea una instancia de UsernamePasswordAuthenticationToken para autenticar al usuario
            if (jwtService.isTokenValid(jwt, userDetails) && isTokenValid) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request, response);
    }
}

