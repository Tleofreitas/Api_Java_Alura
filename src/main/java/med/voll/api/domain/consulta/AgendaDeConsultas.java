package med.voll.api.domain.consulta;

import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.medico.MedicoRepository;
import med.voll.api.domain.paciente.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service // Componente de serviço para regra de negócio e validações
public class AgendaDeConsultas {
    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired // Buscar o Medico
    private MedicoRepository medicoRepository;

    @Autowired // Buscar o Paciente
    private PacienteRepository pacienteRepository;

    public void agendar(DadosAgendamentoConsulta dados) {
        // Checar se Id do Paciente existe
        if (!pacienteRepository.existsById(dados.idPaciente())) {
            throw new ValidacaoException("Id do paciente informado não existe!");
        }

        // Checar se o Id do Médico não é nulo, pois é opcional
        if (dados.idMedico() != null && !medicoRepository.existsById(dados.idMedico())) {
            throw new ValidacaoException("Id do médico informado não existe!");
        }

        // Carregar o paciente pelo id
        var paciente = pacienteRepository.findById(dados.idPaciente()).get();
        // Carregar o medico pelo id
        var medico = escolherMedico(dados);
        // Gerar a Consulta
        var consulta = new Consulta(null, medico, paciente, dados.data());
        // Salvar a consulta no banco de dados
        consultaRepository.save(consulta);
    }

    private Medico escolherMedico(DadosAgendamentoConsulta dados) {
        // Verificar se o médico foi escolhido
        if (dados.idMedico() != null) {
            return medicoRepository.getReferenceById(dados.idMedico());
        }
        // Verificar se a especialidade foi escolhido
        if (dados.especialidade() == null) {
            throw new ValidacaoException("Especialidade é obrigatória quando médico não for escolhido!");
        }
        // Médico aleatório se id nulo e especialidade foi escolhido
        return medicoRepository.escolherMedicoAleatorioLivreNaData(dados.especialidade(), dados.data());
    }
}
