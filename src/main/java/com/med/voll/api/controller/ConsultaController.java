package com.med.voll.api.controller;

import com.med.voll.api.domain.ValidacaoException;
import com.med.voll.api.domain.consulta.AgendaDeConsultas;
import com.med.voll.api.domain.consulta.AgendamentoConsultaDTO;
import com.med.voll.api.domain.consulta.DetalhamentoConsultaDTO;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("consultas")
public class ConsultaController {

    @Autowired
    private AgendaDeConsultas agenda;

    @PostMapping
    @Transactional
    public ResponseEntity agendar(@RequestBody @Valid AgendamentoConsultaDTO dados) throws ValidacaoException {
        var dto = agenda.agendar(dados);
        return ResponseEntity.ok(dto);

    }
}
