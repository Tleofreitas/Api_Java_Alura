package med.voll.api.domain.usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

// Lógica de Autenticação dp Projeto
@Service
public class AutenticacaoService implements UserDetailsService /* Servico de Autenticação*/ {

    @Autowired // Acesso ao banco de dados
    private UsuarioRepository repository;

    @Override // Método chamado automaticamente
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Método para consultar o banco de dados
        return repository.findByLogin(username);
    }
}
