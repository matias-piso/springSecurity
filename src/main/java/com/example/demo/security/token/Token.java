package com.example.demo.security.token;


// La clase Token es una entidad que representa un token de autenticación de un usuario.

import com.example.demo.security.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity (name = "token")
public class Token {

    // El atributo id es la clave primaria de la tabla "token" y se genera automáticamente
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    public Integer id;

    // El atributo token es una cadena que representa el token de autenticación
    @Column(unique = true)
    public String token;

    // El atributo tokenType es una enumeración que indica el tipo de token utilizadow
    @Enumerated(EnumType.STRING)
    public TokenType tokenType = TokenType.BEARER;

    // El atributo revoked es un indicador booleano que indica si el token ha sido revocado
    @Column
    public boolean revoked;

    // El atributo expired es un indicador booleano que indica si el token ha expirado
    @Column
    public boolean expired;

    // El atributo user es una referencia al usuario al que se le ha asignado el token
    @ManyToOne
    @JoinColumn(name = "user_id")
    public User user;
}

// Esta clase se utiliza en la aplicación para generar, almacenar y administrar tokens de autenticación para los usuarios registrados en la base de datos.
// Los tokens se utilizan para autenticar a los usuarios y proporcionar acceso seguro a los recursos protegidos por la aplicación.
// La clase tiene una relación many-to-one con la clase User, lo que significa que un usuario puede tener varios tokens
// y que cada token solo puede pertenecer a un usuario.
// Los atributos de la clase se mapean a la tabla "token" en la base de datos,
// y se utilizan anotaciones para definir la estructura y el comportamiento de la entidad en la base de datos.
