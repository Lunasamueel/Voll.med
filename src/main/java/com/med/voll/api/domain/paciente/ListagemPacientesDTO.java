package com.med.voll.api.domain.paciente;

public record ListagemPacientesDTO(Long id, String nome, String email, String telefone, String cns) {

    public ListagemPacientesDTO(Paciente paciente){
        this(paciente.getId(), paciente.getNome(), paciente.getEmail(), paciente.getTelefone(), paciente.getCns());
    }
}
