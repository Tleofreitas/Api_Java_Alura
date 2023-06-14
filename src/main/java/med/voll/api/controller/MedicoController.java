package med.voll.api.controller;

import med.voll.api.medico.DadosCadastroMedicos;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // Carregar na inicialização do Projeto
@RequestMapping("medicos") // Mapear URL /medicos
public class MedicoController {
    // Métodos das Funcionalidades

    @PostMapping // Post chama o cadastrar()
    public void cadastrar(@RequestBody DadosCadastroMedicos dados) {
        // Request Body = Corpo requisitado do posto = dados do cadastro dos médicos
        System.out.println(dados);
    }
}
