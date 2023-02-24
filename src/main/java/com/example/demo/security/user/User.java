package com.example.demo.security.user;

// Importamos las librerías necesarias para la clase User

import com.example.demo.security.token.Token;
import jakarta.persistence.*;
import java.util.Collection;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

// Anotamos la clase User con la anotación @Entity para indicar que esta clase es una entidad y
// que debe ser mapeada a una tabla en la base de datos
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user")
public class User implements UserDetails {
    // Anotamos el atributo id con la anotación @Id para indicar que este atributo es la clave primaria de la tabla
// Anotamos el atributo id con la anotación @GeneratedValue para indicar que la clave primaria es generada automáticamente
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Integer id;

// Declaramos los atributos que representan los datos del usuario, tales como su nombre, apellido, email y contraseña
    @Column
    private String firstname;
    @Column
    private String lastname;
    @Column
    private String email;
    @Column
    private String password;
// Anotamos el atributo role con la anotación @Enumerated para indicar que este atributo es una enumeración
// y que debe ser mapeado como una cadena en la base de datos
    @Enumerated (EnumType.STRING)
    private Role role;

// Anotamos el atributo tokens con la anotación @OneToMany para indicar que este atributo es una relación de uno a muchos
// con la clase Token. También especificamos que el atributo "user" de la clase Token es el que mapea esta relación
    @OneToMany(mappedBy = "user")
    private List<Token> tokens;



// Implementamos los métodos de la interfaz UserDetails para que Spring Security pueda utilizar esta clase para autenticar usuarios
// El método getAuthorities retorna una colección de las autoridades del usuario, en este caso solo una, correspondiente al rol del usuario
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

// El método getUsername retorna el email del usuario
    @Override
    public String getUsername() {
        return email;
    }
// El método getPassword retorna la contraseña del usuario
    @Override
    public String getPassword() {
        return password;
    }


// Los métodos isAccountNonExpired, isAccountNonLocked y isCredentialsNonExpired
// indican que la cuenta del usuario nunca expira, nunca se bloquea y que sus credenciales nunca expiran
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

// El método isEnabled indica si la cuenta del usuario está habilitada
    @Override
    public boolean isEnabled() {
        return true;
    }
}