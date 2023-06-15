package med.voll.api.medico;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import med.voll.api.endereco.DadosEndereco;

public record DadosCadastroMedicos(
        // Validações com Bean Validation
        @NotBlank // Não pode ser nulo NEM vazio
        String nome,
        @NotBlank
        @Email
        String email,
        @NotBlank
        String telefone,
        @NotBlank
        @Pattern(regexp = "\\d{4,6}") // De 4 a 6 Dígitos
        String crm,
        @NotNull // Não pode ser Nulo
        Especialidade especialidade,
        @NotNull
        @Valid // Validar o outro objeto
        DadosEndereco endereco) {
}
