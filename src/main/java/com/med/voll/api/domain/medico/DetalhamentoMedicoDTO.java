package com.med.voll.api.domain.medico;

public record DetalhamentoMedicoDTO(Long id, String nome, String email, String telefone, String crm,
                                    Especialidade especialidade) {

    public DetalhamentoMedicoDTO(Medico medico){
        this(medico.getId(), medico.getNome(), medico.getEmail(),
                medico.getTelefone(), medico.getCrm(), medico.getEspecialidade());
    }
}
