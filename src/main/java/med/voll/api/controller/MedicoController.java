package med.voll.api.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.medico.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // Carregar na inicialização do Projeto
@RequestMapping("medicos") // Mapear URL /medicos
public class MedicoController {
    // Métodos das Funcionalidades

    @Autowired // Instanciar e passar dentro da classe controller
    private MedicoRepository repository;
    @PostMapping // Post chama o cadastrar() - POST = Inserir dados
    @Transactional // Transação com o banco de dados
    public void cadastrar(@RequestBody @Valid DadosCadastroMedicos dados) { // @Valid para testar as validações
        // Request Body = Corpo requisitado do posto = dados do cadastro dos médicos
        repository.save(new Medico(dados)); // Salvar o JSON no Banco de dados
    }

    // Método de Listagem dos Médicos
    @GetMapping // Post chama a lista de retorno - GET = Pegar dados
    public Page<DadosListagemMedico> listar(@PageableDefault(size = 10, sort = {"nome"}) Pageable pageable) { // Pageable = Paginação
        // Stream e Map para converter lista de Medico para lista de DadosListagemMedico
        return repository.findAllByAtivoTrue(pageable).map(DadosListagemMedico::new);
    }

    @PutMapping // Post chama o atualizar() - PUT = Atualizar dados
    @Transactional
    public void atualizar(@RequestBody @Valid DadosAtualizacaoMedicos dados) {
        //Trazer os dados atuais do médico pelo Id
        var medico = repository.getReferenceById(dados.id());
        medico.atualizarInformacoes(dados);
    }

    @DeleteMapping("/{id}") // Delete chama o excluir() - Deletar dados
    @Transactional
    public void excluir(@PathVariable Long id) { // @PathVariable = Parâmetro Id da URL
        //Trazer os dados atuais do médico pelo Id
        var medico = repository.getReferenceById(id);
        // Trocar o Status para Inativo
        medico.excluir();
    }
}
