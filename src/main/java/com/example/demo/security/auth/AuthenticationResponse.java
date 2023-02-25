package com.example.demo.security.auth;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {

    private String token;
}

//En general, esta clase es un objeto simple de transferencia de datos (DTO)
// que se utiliza para enviar información de autenticación de ida y vuelta entre la aplicación cliente y el servidor.