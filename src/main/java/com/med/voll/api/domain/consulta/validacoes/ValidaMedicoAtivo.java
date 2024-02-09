package com.med.voll.api.domain.consulta.validacoes;

import com.med.voll.api.domain.ValidacaoException;
import com.med.voll.api.domain.consulta.AgendamentoConsultaDTO;
import com.med.voll.api.domain.medico.MedicoRepository;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ValidaMedicoAtivo implements ValidadorAgendamentoConsulta {

    @Autowired
    private MedicoRepository medicoRepository;
    
    public void validar(AgendamentoConsultaDTO dados){
        if(dados.idMedico() == null){
            return;
        }
        
        var medicoEstaAtivo = medicoRepository.findAtivoById(dados.idMedico());
        if(!medicoEstaAtivo) {
            throw new ValidacaoException("consulta nao pode ser agendada com um medico inativo");
        }
    }
}
