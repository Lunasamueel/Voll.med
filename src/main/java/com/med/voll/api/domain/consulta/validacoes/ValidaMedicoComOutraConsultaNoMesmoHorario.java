package com.med.voll.api.domain.consulta.validacoes;

import com.med.voll.api.domain.ValidacaoException;
import com.med.voll.api.domain.consulta.AgendamentoConsultaDTO;
import com.med.voll.api.domain.consulta.ConsultaRepository;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ValidaMedicoComOutraConsultaNoMesmoHorario implements ValidadorAgendamentoConsulta {

    @Autowired
    private ConsultaRepository repository;

    public void validar(AgendamentoConsultaDTO dados){
        var medicoTemOutraConsultaNaMesmaHora = repository.existsByMedicoIdAndData(dados.idMedico(), dados.data());
        if(medicoTemOutraConsultaNaMesmaHora) {
            throw new ValidacaoException("O medico já possui outra consulta no mesmo horario");
        }
    }
}
