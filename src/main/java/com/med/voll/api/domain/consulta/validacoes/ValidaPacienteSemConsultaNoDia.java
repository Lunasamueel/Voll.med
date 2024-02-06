package com.med.voll.api.domain.consulta.validacoes;

import com.med.voll.api.domain.consulta.AgendamentoConsultaDTO;
import com.med.voll.api.domain.consulta.ConsultaRepository;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ValidaPacienteSemConsultaNoDia implements ValidadorAgendamentoConsulta {

    @Autowired
    private ConsultaRepository repository;

    public void validar(AgendamentoConsultaDTO dados) {
        var primeiroHorario = dados.data().withMinute(7);
        var ultimoHorario = dados.data().withHour(18);
        var pacienteTemOutraCOnsultaNoDia = repository.existsByPacienteIdAndDataBetween(dados.idPaciente(), primeiroHorario, ultimoHorario);
        if (pacienteTemOutraCOnsultaNoDia) {
            throw new ValidationException("O paciente já possui outra consulta no mesmo dia");

        }
    }
}
