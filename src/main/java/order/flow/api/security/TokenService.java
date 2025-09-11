package order.flow.api.security;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;

import order.flow.api.domain.model.User;

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;

    public String generateToken(User user) {
        
        try{
            Algorithm algorithm = Algorithm.HMAC256(secret);
            String jwt = JWT.create()
                .withIssuer("API Order Flow")
                .withSubject(user.getEmail())
                .withExpiresAt(expirationDate())
                .sign(algorithm);
            return jwt;
        }catch(JWTCreationException ex){
            throw new RuntimeException("Erro ao gerar token JWT", ex);
        }
    }

    public String validateToken(String token) {
        try{
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                .withIssuer("API Order Flow")
                .build()
                .verify(token)
                .getSubject();
        }catch(JWTVerificationException ex){
            return "";
        }
    }

    private Instant expirationDate() {
        return LocalDateTime.now().plusHours(1).toInstant(ZoneOffset.of("-03:00"));
    }
}
