package com.med.voll.api.controller;

import com.med.voll.api.domain.paciente.*;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("pacientes")
public class PacienteController {

    @Autowired
    private PacienteRepository pacienteRepository;

    @PostMapping
    @Transactional
    public ResponseEntity cadastrar(@RequestBody @Valid CadastroPacienteDTO dados, UriComponentsBuilder componentsBuilder){
        var paciente = new Paciente(dados);
        pacienteRepository.save(paciente);
        var uri = componentsBuilder.path("/pacientes/{id}").buildAndExpand(paciente.getId()).toUri();
        return ResponseEntity.created(uri).body(new DetalhamentoPacienteDTO(paciente));
    }

    @GetMapping
    public ResponseEntity<Page<ListagemPacientesDTO>> listar(@PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao){
        var page = pacienteRepository.findByAtivoTrue(paginacao).map(ListagemPacientesDTO::new);
        return ResponseEntity.ok(page);
    }

    @PutMapping
    @Transactional
    public ResponseEntity atualizarDados(@RequestBody @Valid DadosAtualizacaoPacienteDTO dados){
        var paciente = pacienteRepository.getReferenceById(dados.id());
        paciente.atualizaDados(dados);
        return ResponseEntity.ok(new DetalhamentoPacienteDTO(paciente));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity excluir(@PathVariable Long id){
        var paciente = pacienteRepository.getReferenceById(id);
        paciente.inativar();
        return ResponseEntity.noContent().build();
    }


}
