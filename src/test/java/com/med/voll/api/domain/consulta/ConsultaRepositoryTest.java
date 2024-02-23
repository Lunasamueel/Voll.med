package com.med.voll.api.domain.consulta;

import com.med.voll.api.domain.medico.CadastroMedicoDTO;
import com.med.voll.api.domain.medico.Especialidade;
import com.med.voll.api.domain.medico.Medico;
import com.med.voll.api.domain.paciente.CadastroPacienteDTO;
import com.med.voll.api.domain.paciente.Paciente;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class ConsultaRepositoryTest {

    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    private TestEntityManager entity;

    @Test
    @DisplayName("Deveria retornar true pois o paciente  tem consulta marcada no dia")
    void existsByPacienteIdAndDataBetweenC1() {
        var proxSeg08 = LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.MONDAY)).atTime(8,0);
        var proxSeg18 = LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.MONDAY)).atTime(18,0);
        var medico = cadastrarMedico("Dr chucrute", "medico@voll.med", "123456", Especialidade.CARDIOLOGIA);
        var paciente = cadastrarPaciente("paciente", "paciente@email.com", "123456");
        cadastrarConsulta(medico, paciente, proxSeg08);

        var consulta = consultaRepository.existsByPacienteIdAndDataBetween(paciente.getId(), proxSeg08, proxSeg18);
        //then
        assertThat(consulta).isTrue();
    }

    @Test
    @DisplayName("Deveria retornar false pois o paciente ja tem consulta marcada no dia")
    void existsByPacienteIdAndDataBetweenC2() {
        var proxSeg08 = LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.MONDAY)).atTime(8,0);
        var proxSeg18 = LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.MONDAY)).atTime(18,0);
        var paciente = cadastrarPaciente("paciente", "paciente@email.com", "123456");

        var consulta = consultaRepository.existsByPacienteIdAndDataBetween(paciente.getId(), proxSeg08, proxSeg18);
        //then
        assertThat(consulta).isFalse();
    }

    @Test
    @DisplayName("Deveria retornar true pois o medico ja tem uma consulta no mesmo horario")
    void existsByMedicoIdAndDataC1() {
        var proxSeg08 = LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.MONDAY)).atTime(8,0);
        var medico = cadastrarMedico("Dr chucrute", "medico@voll.med", "123456", Especialidade.CARDIOLOGIA);
        var paciente = cadastrarPaciente("paciente", "paciente@email.com", "123456");
        cadastrarConsulta(medico, paciente, proxSeg08);

        var medicoDisponivel = consultaRepository.existsByMedicoIdAndData(medico.getId(), proxSeg08);
        assertThat(medicoDisponivel).isTrue();

    }

    @Test
    @DisplayName("Deveria retornar false pois o medico nao tem uma consulta no mesmo horario")
    void existsByMedicoIdAndDataC2() {
        var proxSeg08 = LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.MONDAY)).atTime(8,0);
        var medico = cadastrarMedico("Dr chucrute", "medico@voll.med", "123456", Especialidade.CARDIOLOGIA);
        var paciente = cadastrarPaciente("paciente", "paciente@email.com", "123456");
        cadastrarConsulta(medico, paciente, proxSeg08);

        var medicoDisponivel = consultaRepository.existsByMedicoIdAndData(medico.getId(), LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.MONDAY)).atTime(16,0));
        assertThat(medicoDisponivel).isFalse();

    }

    private CadastroPacienteDTO dadosPaciente(String nome, String email, String cns){
        return new CadastroPacienteDTO(
                nome, email, "888888888", cns
        );
    }

    private CadastroMedicoDTO dadosMedico(String nome, String email, String crm, Especialidade especialidade){
        return new CadastroMedicoDTO(nome, email, "99999999", crm, especialidade);
    }

    private Paciente cadastrarPaciente(String nome, String email, String cns){
        var paciente = new Paciente(dadosPaciente(nome, email, cns));
        entity.persist(paciente);
        return paciente;
    }

    private Medico cadastrarMedico(String nome, String email, String crm, Especialidade especialidade){
        var medico = new Medico(dadosMedico(nome, email, crm, especialidade));
        entity.persist(medico);
        return medico;
    }

    private void cadastrarConsulta(Medico medico, Paciente paciente, LocalDateTime data){
        entity.persist(new Consulta(null, medico, paciente, data));
    }
}