package com.med.voll.api.domain.medico;

import com.med.voll.api.domain.consulta.Consulta;
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
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class MedicoRepositoryTest {

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private TestEntityManager em;

    @Test
    @DisplayName("Deveria devolver null quando unico medico cadastrado nao esta disponivel na data")
    void escolherMedicoDisponivelNaDataC1() {
        //given
        var proxSeg10 = LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.MONDAY)).atTime(10,0);

        var medico = cadastrarMedico("medico", "medico@voll.med", "123456", Especialidade.CARDIOLOGIA);
        var paciente = cadastrarPaciente("paciente", "paciente@email.com", "123456");
        cadastrarConsulta(medico, paciente, proxSeg10);
        //when
        var medicoLivre = medicoRepository.escolherMedicoDisponivel(Especialidade.CARDIOLOGIA, proxSeg10);
        //then
        assertThat(medicoLivre).isNull();

    }

    @Test
    @DisplayName("Deveria devolver medico quando ele estiver disponivel na data")
    void escolherMedicoDisponivelNaDataC2() {
        //given ou arrange
        var proxSeg10 = LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.MONDAY)).atTime(10,0);
        var medico = cadastrarMedico("medico", "medico@voll.med", "123456", Especialidade.CARDIOLOGIA);

        //when ou act
        var medicoLivre = medicoRepository.escolherMedicoDisponivel(Especialidade.CARDIOLOGIA, proxSeg10);
        //then ou assert
        assertThat(medicoLivre).isEqualTo(medico);

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
        em.persist(paciente);
        return paciente;
    }

    private Medico cadastrarMedico(String nome, String email, String crm, Especialidade especialidade){
        var medico = new Medico(dadosMedico(nome, email, crm, especialidade));
        em.persist(medico);
        return medico;
    }

    private void cadastrarConsulta(Medico medico, Paciente paciente, LocalDateTime data){
        em.persist(new Consulta(null, medico, paciente, data));
    }

}
