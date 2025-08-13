package com.KahoanDev.libraryapi.controller.common;

import com.KahoanDev.libraryapi.controller.dto.ErrorCampo;
import com.KahoanDev.libraryapi.controller.dto.ErrorResposta;
import com.KahoanDev.libraryapi.exceptions.CampoInvalidoException;
import com.KahoanDev.libraryapi.exceptions.OperacaoNaoPermitidaException;
import com.KahoanDev.libraryapi.exceptions.RegistroDuplicadoException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ErrorResposta handleMethodArgumentNotValidException(MethodArgumentNotValidException e){
        List<FieldError> errors = e.getFieldErrors();
        List<ErrorCampo> lista = errors
                .stream()
                .map(fe -> new ErrorCampo(fe.getField(), fe.getDefaultMessage()))
                .collect(Collectors.toList());
        return new ErrorResposta(
                HttpStatus.UNPROCESSABLE_ENTITY.value(),
                "Erro de validação!",
                lista);
    }

    @ExceptionHandler(RegistroDuplicadoException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResposta handleRegistroDuplicadoException(RegistroDuplicadoException e){
        return ErrorResposta.conflito(e.getMessage());
    }

    @ExceptionHandler(OperacaoNaoPermitidaException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResposta handleOperacaoNaoPermitidaException(OperacaoNaoPermitidaException e){
        return ErrorResposta.respostaPadrao(e.getMessage());
    }

    @ExceptionHandler(CampoInvalidoException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ErrorResposta handleCampoInvalidoException(CampoInvalidoException e){
        return new ErrorResposta(
                HttpStatus.UNPROCESSABLE_ENTITY.value(),
                "Erro de validação",
                List.of(new ErrorCampo(e.getCampo(), e.getMessage())));
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorResposta handleAccessDeniedException(AccessDeniedException e){
        return new ErrorResposta(HttpStatus.FORBIDDEN.value(), "Acesso Negado!", List.of());
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResposta handleErrosNaoTratados(RuntimeException e){
        return new ErrorResposta(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Ocorreu um erro inesperado! Tente novamente mais tarde.",
                List.of());
    }
}
