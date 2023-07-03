package med.voll.api.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.domain.medico.DadosListagemMedico;
import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.medico.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController // Carregar na inicialização do Projeto
@RequestMapping("medicos") // Mapear URL /medicos
@SecurityRequirement(name = "bearer-key")
public class MedicoController {
    // Métodos das Funcionalidades

    @Autowired // Instanciar e passar dentro da classe controller
    private MedicoRepository repository;
    @PostMapping // Post chama o cadastrar() - POST = Inserir dados
    @Transactional // Transação com o banco de dados
    // Retorno 201, @Valid para testar as validações, UriComponentsBuilder cria URI
    public ResponseEntity cadastrar(@RequestBody @Valid DadosCadastroMedicos dados, UriComponentsBuilder uriBuilder) {
        // Request Body = Corpo requisitado do posto = dados do cadastro dos médicos
        var medico = new Medico(dados);
        repository.save(medico); // Salvar o JSON no Banco de dados
        // Criar objeto URI (endereço da API)
        var uri = uriBuilder.path("/medicos/{id}").buildAndExpand(medico.getId()).toUri();
        // Retorna URI + DTO
        return ResponseEntity.created(uri).body(new DadosDetalhamentoMedico(medico));
    }

    // Método de Listagem dos Médicos
    @GetMapping // Post chama a lista de retorno - GET = Pegar dados
    // Retorno 200, Pageable = Paginação
    public ResponseEntity<Page<DadosListagemMedico>> listar(@PageableDefault(size = 10, sort = {"nome"}) Pageable pageable) {
        // Stream e Map para converter lista de Medico para lista de DadosListagemMedico
        var page = repository.findAllByAtivoTrue(pageable).map(DadosListagemMedico::new);
        // Processado com Sucesso e retorna o conteúdo
        return ResponseEntity.ok(page);
    }

    @PutMapping // Post chama o atualizar() - PUT = Atualizar dados
    @Transactional
    public ResponseEntity<DadosDetalhamentoMedico> atualizar(@RequestBody @Valid DadosAtualizacaoMedicos dados) {
        //Trazer os dados atuais do médico pelo Id
        var medico = repository.getReferenceById(dados.id());
        medico.atualizarInformacoes(dados);
        // Devolver dados do médico atualizado, porém não deve usar a entidade JPA, por isso criasse um DTO
        return ResponseEntity.ok(new DadosDetalhamentoMedico(medico));
    }

    @DeleteMapping("/{id}") // Delete chama o excluir() - Deletar dados
    @Transactional
    // Retorno 204
    public ResponseEntity excluir(@PathVariable Long id) { // @PathVariable = Parâmetro Id da URL
        //Trazer os dados atuais do médico pelo Id
        var medico = repository.getReferenceById(id);
        // Trocar o Status para Inativo
        medico.excluir();
        // Processado com Sucesso e não tem conteúdo na resposta
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}") // Detalhamento do Médico
    // Retorno 200
    public ResponseEntity detalhar(@PathVariable Long id) { // @PathVariable = Parâmetro Id da URL
        //Trazer os dados atuais do médico pelo Id
        var medico = repository.getReferenceById(id);
        // Retorno com DTO de detalhamento
        return ResponseEntity.ok(new DadosDetalhamentoMedico(medico));
    }
}
