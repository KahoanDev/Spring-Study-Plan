package com.KahoanDev.libraryapi.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.UUID;

public record AutorDTO(
        UUID id,
        @NotBlank(message = "campo obrigatório!")
        @Size(min = 2, max = 100, message = "campo fora do tamanho padrão!")
        String nome,
        @NotNull(message = "campo obrigatório!")
        @Past(message = "não pode ser uma data futura!")
        LocalDate dataNascimento,
        @NotBlank(message = "campo obrigatório!")
        @Size(min = 2, max = 50, message = "campo fora do tamanho padrão!")
        String nacionalidade) {
}
