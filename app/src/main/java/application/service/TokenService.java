package application.service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;

import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.JWT;

import application.model.Usuario;

@Service
public class TokenService {
    @Value("${api.security.token.key}")
    private String tokenKey;
    
    public String generateToken(Usuario usuario) {
        //System.out.println(tokenKey);
        try {
            Algorithm algorithm = Algorithm.HMAC256(tokenKey);
             return JWT.create()
                .withIssuer("API Quizzes")
                .withSubject(usuario.getNomeDeUsuario())
                .withExpiresAt(expirationDate())
                .sign(algorithm);
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Erro ao gerar JWT");
        }
    }

    public String getSubject(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(tokenKey);
            return JWT.require(algorithm)
                .withIssuer("API Quizzes")
                .build()
                .verify(token)
                .getSubject();
        } catch (JWTVerificationException exception) {
            throw new RuntimeException("Token Inválido");
        }
    }

    private Instant expirationDate() {
        return LocalDateTime.now().plusHours(2)
            .toInstant(ZoneOffset.of("-03:00"));
    }
}
