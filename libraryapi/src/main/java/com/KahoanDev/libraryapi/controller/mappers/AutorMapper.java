package com.KahoanDev.libraryapi.controller.mappers;

import com.KahoanDev.libraryapi.controller.dto.AutorDTO;
import com.KahoanDev.libraryapi.model.Autor;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AutorMapper {

    Autor toEntity(AutorDTO dto);

    AutorDTO toDTO(Autor autor);
}
