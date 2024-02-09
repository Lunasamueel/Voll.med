package com.med.voll.api.domain.paciente;

import jakarta.validation.constraints.NotNull;

public record DadosAtualizacaoPacienteDTO(
        @NotNull
        Long id,
        String nome,
        String email,
        String telefone
) {
}
