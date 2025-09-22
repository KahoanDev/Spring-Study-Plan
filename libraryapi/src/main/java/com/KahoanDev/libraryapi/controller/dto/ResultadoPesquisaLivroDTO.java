package com.KahoanDev.libraryapi.controller.dto;

import com.KahoanDev.libraryapi.model.enums.GeneroLivro;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Schema(name = "ResultadoPesquisaLivro")
public record ResultadoPesquisaLivroDTO (
        UUID id,
        String isbn,
        String titulo,
        LocalDate dataPublicacao,
        GeneroLivro genero,
        BigDecimal preco,
        AutorDTO autor
){
}
