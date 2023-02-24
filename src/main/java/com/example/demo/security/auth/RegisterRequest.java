package com.example.demo.security.auth;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    private String firstname;  // Nombre del usuario.
    private String lastname;   // Apellido del usuario.
    private String email;      // Correo electrónico del usuario.
    private String password;   // Contraseña del usuario.
}
