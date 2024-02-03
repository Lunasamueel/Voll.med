package com.med.voll.api.domain.paciente;

public record DadosAtualizacaoPacienteDTO(
        Long id,
        String nome,
        String email,
        String telefone
) {
}
