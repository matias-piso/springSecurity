package com.example.demo.security.user;

// La interfaz UserRepository es una extensión de JpaRepository que define métodos para interactuar con la base de datos.

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    // El método findByEmail busca en la base de datos un usuario con el correo electrónico especificado
    Optional<User> findByEmail(String email);
}

// En esta interfaz, se hereda de JpaRepository, especificando que el tipo de entidad es User y que la clave primaria es de tipo Integer.
// Además, se define un método findByEmail que devuelve un Optional de User y que se utiliza para buscar un usuario por su correo electrónico.
// Al devolver un Optional, se permite que el resultado de la búsqueda sea nulo si el correo electrónico no está registrado en la base de datos.