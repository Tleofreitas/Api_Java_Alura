package med.voll.api.domain.medico;

// DTO dos dados de retorno da busca de médicos
public record DadosListagemMedico(Long id, String nome, String email, String crm, Especialidade especialidade) {
    public DadosListagemMedico(Medico medico) {
        // A partir do médico cria o construtor do DadosListagemMedico
        this(
                medico.getId(),
                medico.getNome(),
                medico.getEmail(),
                medico.getCrm(),
                medico.getEspecialidade()
        );
    }
}
