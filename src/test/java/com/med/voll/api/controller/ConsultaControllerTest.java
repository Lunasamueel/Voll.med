package com.med.voll.api.controller;

import com.med.voll.api.domain.consulta.AgendaDeConsultas;
import com.med.voll.api.domain.consulta.AgendamentoConsultaDTO;
import com.med.voll.api.domain.consulta.DetalhamentoConsultaDTO;
import com.med.voll.api.domain.medico.Especialidade;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser
@AutoConfigureJsonTesters
class ConsultaControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private JacksonTester<AgendamentoConsultaDTO> agendamentoConsultaDTOJson;
    @Autowired
    private JacksonTester<DetalhamentoConsultaDTO> detalhamentoConsultaDTOjson;

    @MockBean
    private AgendaDeConsultas agendaDeConsultas;

    @Test
    @DisplayName("Deveria devolver código http 400 quando info estiverem inválidas")
    void agendar_c1() throws Exception {
        var response = mvc.perform(post("/consultas"))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("Deveria devolver código http 200 quando info estiverem válidas")
    void agendar_c2() throws Exception {
        var data = LocalDateTime.now().plusHours(1);
        var dadosDetalhamento = new DetalhamentoConsultaDTO(null, 21L, 51L, data);
        when(agendaDeConsultas.agendar(any()))
                .thenReturn(dadosDetalhamento);

        var response =
                mvc.perform(post("/consultas")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(agendamentoConsultaDTOJson.write(
                                            new AgendamentoConsultaDTO(21L,11L, data, Especialidade.CARDIOLOGIA)
                                    ).getJson())
                )
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

        var jsonEsperado = detalhamentoConsultaDTOjson.write(
                dadosDetalhamento
        ).getJson();

        assertThat(response.getContentAsString()).isEqualTo(jsonEsperado);
    }

}