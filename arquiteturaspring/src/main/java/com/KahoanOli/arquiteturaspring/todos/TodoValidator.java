package com.KahoanOli.arquiteturaspring.todos;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class TodoValidator {

    private TodoRepository repository;

    public void validar(TodoEntity todo){
        if(existeTodoComDescricao(todo.getDescricao())){
            throw new IllegalArgumentException("Já existe um Todo com essa descrição");
        }
    }

    public boolean existeTodoComDescricao(String descricao){
        return repository.existsByDescricao(descricao);
    }
}
