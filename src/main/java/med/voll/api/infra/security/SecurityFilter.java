package med.voll.api.infra.security;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import med.voll.api.domain.usuario.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {
    // Interceptando requisições e validando token

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioRepository repository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // Recuperar o TOKEN do cabeçalho autorization
        var tokenJWT = recuperarToken(request);

        // Verificar se TOKEN está correto e pegar o login
        if (tokenJWT != null) {
            var subject = tokenService.getSubject(tokenJWT);

            // TOKEN está válido
            // Recuperar o usuário
            var usuario =repository.findByLogin(subject);
            // Realizar a autenticação
            var authentication = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
            // Forçar autenticação forçada
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        // Chamar próximo filtro
        filterChain.doFilter(request, response);
    }

    private String recuperarToken(HttpServletRequest request) {
        // Pegar cabeçalho autorization
        var authorizationHeader = request.getHeader("Authorization");
        // Testar se está vazio ou nulo
        if (authorizationHeader != null) {
            // Retornar o TOKEN
            return authorizationHeader.replace("Bearer ", "");
        }

        return null;
    }
}
