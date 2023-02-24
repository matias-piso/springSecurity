package com.example.demo.security.auth;

import com.example.demo.security.config.JwtService;
import com.example.demo.security.token.Token;
import com.example.demo.security.token.TokenRepository;
import com.example.demo.security.token.TokenType;
import com.example.demo.security.user.Role;
import com.example.demo.security.user.User;
import com.example.demo.security.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
//Este código define la clase RegisterRequest, que se utiliza para representar una solicitud de registro de un nuevo usuario en la aplicación.
// Esta clase utiliza las siguientes anotaciones:
//@Data: esta anotación de Lombok genera automáticamente getters, setters, métodos `
@Service
@RequiredArgsConstructor
public class AuthenticationService {
    @Autowired
    private  UserRepository repository; // Inyección de dependencia para UserRepository
    @Autowired
    private  TokenRepository tokenRepository; // Inyección de dependencia para TokenRepository
    @Autowired
    private  PasswordEncoder passwordEncoder; // Inyección de dependencia para PasswordEncoder
    @Autowired
    private  JwtService jwtService; // Inyección de dependencia para JwtService
    @Autowired
    private  AuthenticationManager authenticationManager; // Inyección de dependencia para AuthenticationManager

    public AuthenticationResponse register(RegisterRequest request) {
        var user = User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword())) // Codifica la contraseña antes de guardarla en la base de datos
                .role(Role.USER)
                .build();
        var savedUser = repository.save(user); // Guarda el usuario en la base de datos
        var jwtToken = jwtService.generateToken(user); // Genera un token JWT para el usuario
        saveUserToken(savedUser, jwtToken); // Guarda el token en la base de datos
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        ); // Autentica al usuario usando el AuthenticationManager
        var user = repository.findByEmail(request.getEmail())
                .orElseThrow(); // Busca al usuario en la base de datos
        var jwtToken = jwtService.generateToken(user); // Genera un token JWT para el usuario
        revokeAllUserTokens(user); // Revoca todos los tokens anteriores del usuario
        saveUserToken(user, jwtToken); // Guarda el nuevo token en la base de datos
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    private void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build(); // Crea un objeto Token y lo guarda en la base de datos
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId()); // Busca todos los tokens válidos del usuario
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        }); // Revoca todos los tokens válidos del usuario
        tokenRepository.saveAll(validUserTokens);
    }
}