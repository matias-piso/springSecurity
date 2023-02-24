package com.example.demo.security.token;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepository extends JpaRepository<Token, Integer> {

    // Busca todos los tokens válidos para un usuario dado.
    @Query(value = "SELECT * FROM token t JOIN user u ON t.user_id = u.id WHERE u.id = :id AND (t.expired = false OR t.revoked = false)", nativeQuery = true)
    List<Token> findAllValidTokenByUser(Integer id);


    // Busca un token por su valor.
    Optional<Token> findByToken(String token);
}