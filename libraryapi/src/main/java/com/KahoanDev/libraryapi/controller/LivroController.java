package com.KahoanDev.libraryapi.controller;

import com.KahoanDev.libraryapi.controller.dto.CadastroLivroDTO;
import com.KahoanDev.libraryapi.controller.dto.ErrorResposta;
import com.KahoanDev.libraryapi.controller.dto.ResultadoPesquisaLivroDTO;
import com.KahoanDev.libraryapi.controller.mappers.LivroMapper;
import com.KahoanDev.libraryapi.exceptions.RegistroDuplicadoException;
import com.KahoanDev.libraryapi.model.Livro;
import com.KahoanDev.libraryapi.service.LivroService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("livros")
@RequiredArgsConstructor
public class LivroController implements GenericController{

    private final LivroService service;
    private final LivroMapper mapper;

    @PostMapping
    public ResponseEntity<Void> salvar(@RequestBody @Valid CadastroLivroDTO dto){
        // mapear dto para entidade
        Livro livro = mapper.toEntity(dto);

        // enviar a entidade para o service validar e salvar na base
        service.salvar(livro);

        // criar url para acesso dos dados do livro
        var uri = gerarHeaderLocation(dto.idAutor());

        // retornar código created com header location
        return ResponseEntity.created(uri).build();
    }

    @GetMapping("{id}")
    public ResponseEntity<ResultadoPesquisaLivroDTO> obterDetalhes(
            @PathVariable("id") String id){
        return service.obterPorId(UUID.fromString(id))
                .map(livro -> {
                    var dto  = mapper.toDTO(livro);
                    return ResponseEntity.ok(dto);
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> deletar(@PathVariable("id") String id){
        return service.obterPorId(UUID.fromString(id))
                .map(livro -> {
                    service.deletar(livro);
                    return ResponseEntity.noContent().build();
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
