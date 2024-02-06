package com.med.voll.api.domain.consulta.validacoes;

import com.med.voll.api.domain.consulta.AgendamentoConsultaDTO;
import jakarta.validation.ValidationException;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;

@Service
public class ValidaHoraFuncionamentoClinica implements ValidadorAgendamentoConsulta {

    public void validar(AgendamentoConsultaDTO dados){
        var dataConsulta = dados.data();
        var domingo = dataConsulta.getDayOfWeek().equals(DayOfWeek.SUNDAY);

        var horaAberturaClinica = dataConsulta.getHour() < 7;
        var horaFechamentoClinica = dataConsulta.getHour() > 18;

        if(domingo || horaAberturaClinica || horaFechamentoClinica){
            throw new ValidationException("Consulta Fora do horario de funcionamento");
        }


    }
}
