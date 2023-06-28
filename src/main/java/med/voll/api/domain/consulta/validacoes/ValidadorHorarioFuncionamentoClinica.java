package med.voll.api.domain.consulta.validacoes;

import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.consulta.DadosAgendamentoConsulta;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;

@Component
public class ValidadorHorarioFuncionamentoClinica {
    // Seg a sábado das 7 as 19
    public void validar(DadosAgendamentoConsulta dados) {
        var dataConsulta = dados.data();

        // Validar Domingo
        var domingo = dataConsulta.getDayOfWeek().equals(DayOfWeek.SUNDAY);
        // Validar Hora
        var antesDaAberturaDaClinica = dataConsulta.getHour() < 7;
        // Validar Hora Encerramento
        var depoisDoEncerramentoDaClinica = dataConsulta.getHour() > 18;

        if (domingo || antesDaAberturaDaClinica || depoisDoEncerramentoDaClinica) {
            throw new ValidacaoException("Consulta fora do horário de funcionamento da clínica");
        }
    }
}
