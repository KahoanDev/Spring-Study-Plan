package com.KahoanDev.libraryapi.controller;

import com.KahoanDev.libraryapi.controller.dto.UsuarioDTO;
import com.KahoanDev.libraryapi.controller.mappers.UsuarioMapper;
import com.KahoanDev.libraryapi.repository.UsuarioRepository;
import com.KahoanDev.libraryapi.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioRepository repository;
    private final UsuarioService service;
    private final UsuarioMapper mapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void salvar(@RequestBody @Valid UsuarioDTO dto){
        var usuario = mapper.toEntity(dto);
        service.salvar(usuario);
    }
}
