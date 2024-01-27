package com.med.voll.api.domain.medico;

public record DadosAtualizacaoMedicoDTO(
        Long id,
        String nome,
        String email,
        String telefone
) {
}
