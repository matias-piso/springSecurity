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
//La clase AuthenticationResponse es una clase de modelo que representa la respuesta a una solicitud de autenticación. Tiene los siguientes campos:
//
//token: Un token JWT que se utilizará para realizar solicitudes autenticadas.
//La anotación @Data es de Lombok y genera automáticamente los métodos toString, equals, hashCode, Getter y Setter.
//
//La anotación @Builder es otra anotación de Lombok que se utiliza para generar un constructor de tipo constructor de constructor, lo que hace que sea fácil de crear instancias de objetos de esta clase.
//
//La anotación @AllArgsConstructor genera un constructor con todos los campos de la clase como argumentos.
//
//La anotación @NoArgsConstructor genera un constructor sin argumentos.
//
//En general, esta clase es un objeto simple de transferencia de datos (DTO)
// que se utiliza para enviar información de autenticación de ida y vuelta entre la aplicación cliente y el servidor.