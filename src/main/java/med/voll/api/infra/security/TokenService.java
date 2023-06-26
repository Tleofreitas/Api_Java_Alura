package med.voll.api.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import med.voll.api.domain.usuario.Usuario;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${api.security.token.secret}") // Pegar o secret do application.properties
    private String secret;

    // Geração do TOKEN
    public String gerarToken(Usuario usuario) {
        try {
            var algoritmo = Algorithm.HMAC256(secret); // secret = senha da API
            return JWT.create()
                    .withIssuer("API Voll.med") // Quem está gerando o TOKEN
                    .withSubject(usuario.getLogin()) // Qual o usuário
                    .withExpiresAt(dataExpiracao()) // Validade do TOKEN
                    .sign(algoritmo);
        } catch (JWTVerificationException exception){
            throw new RuntimeException("Erro ao gerar TOKEN JWT", exception);
        }
    }

    // Devolver o usuário se o token for validado
    public String getSubject(String tokenJWT) {
        try {
            var algoritmo = Algorithm.HMAC256(secret); // secret = senha da API
            return JWT.require(algoritmo)
                    .withIssuer("API Voll.med")
                    .build()
                    .verify(tokenJWT)
                    .getSubject();
        } catch (JWTVerificationException exception){
            // Excessão para Token inválido, incorreto ou expirado
            throw new RuntimeException("Token JWT inválido, incorreto ou expirado!");
        }
    }

    private Instant dataExpiracao() {
        // Validade de 2 horas
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}
