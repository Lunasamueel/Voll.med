package com.med.voll.api.domain.consulta.validacoes;

import com.med.voll.api.domain.ValidacaoException;
import com.med.voll.api.domain.consulta.AgendamentoConsultaDTO;
import jakarta.validation.ValidationException;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDateTime;
@Service
public class ValidaHorarioAntecedencia implements ValidadorAgendamentoConsulta {

    public void validar(AgendamentoConsultaDTO dados){
        var dataConsulta = dados.data();
        var agora = LocalDateTime.now();
        var diferencaMinutos = Duration.between(agora, dataConsulta).toMinutes();



        if(diferencaMinutos < 30){
            throw new ValidacaoException("Consulta deve ser agendada com no minimo 30 minutos.");
        }


    }
}
