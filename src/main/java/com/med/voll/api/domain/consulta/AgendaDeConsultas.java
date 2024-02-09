package com.med.voll.api.domain.consulta;

import com.med.voll.api.domain.ValidacaoException;
import com.med.voll.api.domain.consulta.validacoes.ValidadorAgendamentoConsulta;
import com.med.voll.api.domain.medico.Medico;
import com.med.voll.api.domain.medico.MedicoRepository;
import com.med.voll.api.domain.paciente.Paciente;
import com.med.voll.api.domain.paciente.PacienteRepository;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AgendaDeConsultas {

    @Autowired
    private ConsultaRepository consultaRepository;
    @Autowired
    private MedicoRepository medicoRepository;
    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private List<ValidadorAgendamentoConsulta> validadores;


    public DetalhamentoConsultaDTO agendar(AgendamentoConsultaDTO dados) throws ValidacaoException {

        if(!pacienteRepository.existsById(dados.idPaciente())){
            throw new ValidacaoException("O id do paciente informado não existe.");
        }

        if(dados.idMedico() != null && !medicoRepository.existsById(dados.idMedico())){
            throw new ValidacaoException("O id do medico informado não existe.");
        }

        validadores.forEach(v -> v.validar(dados));

        var paciente = pacienteRepository.getReferenceById(dados.idPaciente());
        var medico = escolherMedico(dados);
        var consulta = new Consulta(null, medico, paciente, dados.data());
        consultaRepository.save(consulta);

        return new DetalhamentoConsultaDTO(consulta);
    }

    private Medico escolherMedico(AgendamentoConsultaDTO dados) {
       if(dados.idMedico() != null){
           return medicoRepository.getReferenceById(dados.idMedico());
       }

        if(dados.especialidade() == null){
            throw  new ValidacaoException("Especialidade obrigatória.");
        }

        return medicoRepository.escolherMedicoDisponivel(dados.especialidade(), dados.data());
       
    }
}
