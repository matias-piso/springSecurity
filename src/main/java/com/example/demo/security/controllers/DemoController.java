package com.example.demo.security.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // Esta anotación marca la clase como un controlador de Spring MVC
@RequestMapping("/api/v1/demo-controller") // Esta anotación especifica la ruta base para este controlador
public class DemoController {

    @GetMapping // Esta anotación especifica que este método maneja solicitudes HTTP GET
    public ResponseEntity<String> sayHello() { // Este método devuelve una respuesta HTTP con un cuerpo de tipo String
        return ResponseEntity.ok("Hello from secured endpoint"); // Devuelve una respuesta HTTP 200 OK con el mensaje especificado en el cuerpo de la respuesta
    }

}
