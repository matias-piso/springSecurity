package com.example.demo.security.auth;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// Se importa la anotación de la librería Lombok para generar automáticamente los métodos Getter, Setter, ToString, EqualsAndHashCode y RequiredArgsConstructor
@Data
// Anotación de Lombok para generar un constructor con todos los argumentos requeridos
@AllArgsConstructor
// Anotación de Lombok para generar un constructor vacío
@NoArgsConstructor
// Anotación de Lombok para generar un patrón de diseño Builder
@Builder
public class AuthenticationRequest {

    // Atributo de tipo String que representa el email
    private String email;

    // Atributo de tipo String que representa la contraseña
    String password;
}