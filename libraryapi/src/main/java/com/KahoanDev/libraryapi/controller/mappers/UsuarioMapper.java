package com.KahoanDev.libraryapi.controller.mappers;

import com.KahoanDev.libraryapi.controller.dto.UsuarioDTO;
import com.KahoanDev.libraryapi.model.Usuario;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    Usuario toEntity(UsuarioDTO dto);
}
