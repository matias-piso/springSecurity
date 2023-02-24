package com.example.demo.security.config;

import com.example.demo.security.token.TokenRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;
@Service
@RequiredArgsConstructor
public class LogoutService implements LogoutHandler {
    @Autowired
    private  TokenRepository tokenRepository;

    @Override
    public void logout(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) {
        // Obtener el token JWT del encabezado Authorization de la solicitud HTTP
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        // Si no hay un encabezado Authorization o no comienza con "Bearer", salir de la función
        if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
            return;
        }
        // Extraer el token de autorización de la solicitud HTTP
        jwt = authHeader.substring(7);
        // Buscar el token en la base de datos de tokens
        var storedToken = tokenRepository.findByToken(jwt)
                .orElse(null);
        // Si el token se encuentra en la base de datos, marcarlo como caducado y revocado, y guardar el cambio en la base de datos
        // Limpiar el contexto de seguridad para asegurarse de que el usuario no tenga acceso no autorizado a recursos protegidos después del cierre de sesión.
        if (storedToken != null) {
            storedToken.setExpired(true);
            storedToken.setRevoked(true);
            tokenRepository.save(storedToken);
            SecurityContextHolder.clearContext();
        }
    }
}
/*
En resumen, este servicio se encarga de procesar solicitudes de cierre de sesión.
 El método logout se llama cuando un usuario envía una solicitud de cierre de sesión al servidor.
  La función extrae el token JWT del encabezado Authorization de la solicitud HTTP y busca el token en la base de datos de tokens.
   Si el token se encuentra en la base de datos, se marca como caducado y revocado, y se guarda el cambio en la base de datos.
    También se limpia el contexto de seguridad para asegurarse de que el usuario no tenga acceso no autorizado a recursos protegidos después del cierre de sesión.
*/