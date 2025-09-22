package com.KahoanDev.libraryapi.controller;

import com.KahoanDev.libraryapi.controller.dto.UsuarioDTO;
import com.KahoanDev.libraryapi.controller.mappers.UsuarioMapper;
import com.KahoanDev.libraryapi.repository.UsuarioRepository;
import com.KahoanDev.libraryapi.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("usuarios")
@RequiredArgsConstructor
@Tag(name = "Usuarios")
public class UsuarioController {

    private final UsuarioRepository repository;
    private final UsuarioService service;
    private final UsuarioMapper mapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Salvar", description = "Cadastrar novo usuário")
    @ApiResponse(responseCode = "201", description = "Usuário cadastrado com sucesso!")
    public void salvar(@RequestBody @Valid UsuarioDTO dto){
        var usuario = mapper.toEntity(dto);
        service.salvar(usuario);
    }
}
