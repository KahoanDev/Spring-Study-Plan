package com.KahoanDev.libraryapi.controller;

import com.KahoanDev.libraryapi.controller.dto.CadastroLivroDTO;
import com.KahoanDev.libraryapi.controller.dto.ResultadoPesquisaLivroDTO;
import com.KahoanDev.libraryapi.controller.mappers.LivroMapper;
import com.KahoanDev.libraryapi.model.Livro;
import com.KahoanDev.libraryapi.model.enums.GeneroLivro;
import com.KahoanDev.libraryapi.service.LivroService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("livros")
@RequiredArgsConstructor
@Tag(name = "Livros")
public class LivroController implements GenericController{

    private final LivroService service;
    private final LivroMapper mapper;

    @PostMapping
    @PreAuthorize("hasAnyRole('OPERADOR', 'GERENTE')")
    @Operation(summary = "Salvar", description = "Cadastrar novo livro")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Livro cadastrado com sucesso!"),
            @ApiResponse(responseCode = "409", description = "ISBN já Cadastrado!"),
            @ApiResponse(responseCode = "422", description = "Para livros com ano de publicação a partir de 2020, o preço é obrigatório!")
    })
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
    @PreAuthorize("hasAnyRole('OPERADOR', 'GERENTE')")
    @Operation(summary = "Obter Detalhes", description = "Retorna os dados do Livro pelo ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Livro encontrado."),
            @ApiResponse(responseCode = "404", description = "Livro não encontrado.")
    })
    public ResponseEntity<ResultadoPesquisaLivroDTO> obterDetalhes(@PathVariable("id") String id){
        return service.obterPorId(UUID.fromString(id))
                .map(livro -> {
                    var dto  = mapper.toDTO(livro);
                    return ResponseEntity.ok(dto);
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasAnyRole('OPERADOR', 'GERENTE')")
    @Operation(summary = "Deletar", description = "Deleta um Livro existente.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Deletado com Sucesso!"),
            @ApiResponse(responseCode = "404", description = "Livro não encontrado.")
    })
    public ResponseEntity<Object> deletar(@PathVariable("id") String id){
        return service.obterPorId(UUID.fromString(id))
                .map(livro -> {
                    service.deletar(livro);
                    return ResponseEntity.noContent().build();
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('OPERADOR', 'GERENTE')")
    @Operation(summary = "Pesquisar", description = "Pesquisa livros por parâmetros.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sucesso!")
    })
    public ResponseEntity<Page<ResultadoPesquisaLivroDTO>> pesquisa(
            @RequestParam(value = "isbn", required = false)
            String isbn,
            @RequestParam(value = "titulo", required = false)
            String titulo,
            @RequestParam(value = "genero", required = false)
            GeneroLivro genero,
            @RequestParam(value = "nome-autor", required = false)
            String nomeAutor,
            @RequestParam(value = "ano-publicacao", required = false)
            Integer anoPublicacao,
            @RequestParam(value = "pagina", defaultValue = "0")
            Integer pagina,
            @RequestParam(value = "tamanho-pagina", defaultValue = "10")
            Integer tamanhoPagina
    ){
        Page<Livro> paginaResultado = service.pesquisa(isbn, titulo, nomeAutor, genero, anoPublicacao, pagina, tamanhoPagina);

        Page<ResultadoPesquisaLivroDTO> resultado = paginaResultado.map(mapper::toDTO);

        return ResponseEntity.ok(resultado);
    }

    @PutMapping("{id}")
    @PreAuthorize("hasAnyRole('OPERADOR', 'GERENTE')")
    @Operation(summary = "Atualizar", description = "Atualiza um Livro existente.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Atualizado com Sucesso!"),
            @ApiResponse(responseCode = "409", description = "ISBN já Cadastrado!"),
            @ApiResponse(responseCode = "422", description = "Para livros com ano de publicação a partir de 2020, o preço é obrigatório!")
    })
    public ResponseEntity<Object> atualiza(@PathVariable String id, @RequestBody @Valid CadastroLivroDTO dto){
        return service.obterPorId(UUID.fromString(id)).map(
                livro-> {
                    Livro entidade = mapper.toEntity(dto);
                    livro.setDataPublicacao(entidade.getDataPublicacao());
                    livro.setIsbn(entidade.getIsbn());
                    livro.setPreco(entidade.getPreco());
                    livro.setGenero(entidade.getGenero());
                    livro.setTitulo(entidade.getTitulo());
                    livro.setAutor(entidade.getAutor());

                    service.atualizar(livro);

                    return ResponseEntity.noContent().build();
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
