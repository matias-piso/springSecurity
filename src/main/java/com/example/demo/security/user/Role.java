package com.example.demo.security.user;

// La clase Role es una enumeración que define los posibles roles de un usuario en el sistema

public enum Role {

    // Se definen los dos roles posibles, USER y ADMIN
    USER,
    ADMIN
}

// Esta enumeración es utilizada en la clase User para asignar un rol a cada usuario.
// La anotación @Enumerated de la clase User indica que el atributo "role" es de tipo enumeración y que debe ser mapeado como una cadena en la base de datos.
// Además, en el método getAuthorities de la clase User, se utiliza el nombre del rol como el nombre de la autoridad correspondiente al usuario.