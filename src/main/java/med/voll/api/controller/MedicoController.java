package med.voll.api.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.medico.DadosCadastroMedicos;
import med.voll.api.medico.Medico;
import med.voll.api.medico.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // Carregar na inicialização do Projeto
@RequestMapping("medicos") // Mapear URL /medicos
public class MedicoController {
    // Métodos das Funcionalidades

    @Autowired // Instanciar e passar dentro da classe controller
    private MedicoRepository repository;
    @PostMapping // Post chama o cadastrar()
    @Transactional // Transação com o banco de dados
    public void cadastrar(@RequestBody @Valid DadosCadastroMedicos dados) { // @Valid para testar as validações
        // Request Body = Corpo requisitado do posto = dados do cadastro dos médicos
        repository.save(new Medico(dados)); // Salvar o JSON no Banco de dados
    }
}
