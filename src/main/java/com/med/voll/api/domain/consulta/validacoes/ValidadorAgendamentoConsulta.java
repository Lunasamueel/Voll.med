package com.med.voll.api.domain.consulta.validacoes;

import com.med.voll.api.domain.consulta.AgendamentoConsultaDTO;

public interface ValidadorAgendamentoConsulta {

    void validar(AgendamentoConsultaDTO dados);
}
