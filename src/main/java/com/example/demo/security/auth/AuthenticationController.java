package com.example.demo.security.auth;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    // Inyecta la dependencia de AuthenticationService mediante el constructor
    @Autowired
    private  AuthenticationService service;

    // Maneja la solicitud POST para el registro de un nuevo usuario
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest request // Anotación para deserializar el cuerpo de la solicitud en un objeto RegisterRequest
    ) {
// Llama al método "register" de AuthenticationService y envía la solicitud como parámetro
        return ResponseEntity.ok(service.register(request));
    }

    // Maneja la solicitud POST para autenticar un usuario existente
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request // Anotación para deserializar el cuerpo de la solicitud en un objeto AuthenticationRequest
    ) {
// Llama al método "authenticate" de AuthenticationService y envía la solicitud como parámetro
        return ResponseEntity.ok(service.authenticate(request));
    }
}