package med.voll.api.domain.consulta;

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
        // Carregar o paciente pelo id
        var paciente = pacienteRepository.findById(dados.idPaciente()).get();
        // Carregar o medico pelo id
        var medico = medicoRepository.findById(dados.idMedico()).get();
        // Gerar a Consulta
        var consulta = new Consulta(null, medico, paciente, dados.data());
        // Salvar a consulta no banco de dados
        consultaRepository.save(consulta);
    }
}
