package com.med.voll.api.controller;

import com.med.voll.api.domain.usuario.DadosLoginDTO;
import com.med.voll.api.domain.usuario.Usuario;
import com.med.voll.api.infra.security.DadosTokenJWT;
import com.med.voll.api.infra.security.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AutenticacaoController {

    @Autowired
    private AuthenticationManager manager;
    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity efetuarLogin(@RequestBody @Valid DadosLoginDTO dados){
        var authToken = new UsernamePasswordAuthenticationToken(dados.login(), dados.senha());

        var auth = manager.authenticate(authToken);

        var tokenJWT = tokenService.gerarToken((Usuario) auth.getPrincipal());

        return ResponseEntity.ok(new DadosTokenJWT(tokenJWT));
    }
}
