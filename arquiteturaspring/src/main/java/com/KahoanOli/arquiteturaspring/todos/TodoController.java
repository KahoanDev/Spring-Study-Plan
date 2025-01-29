package com.KahoanOli.arquiteturaspring.todos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("todos")
public class TodoController {

    @Autowired
    private TodoService service;

    @PostMapping
    public TodoEntity salvar(@RequestBody TodoEntity todo){
        try {
            return this.service.salvar(todo);
        }catch (IllegalArgumentException e){
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    @PutMapping("{id}")
    public void atualizarStatus(@PathVariable("id") Integer id, TodoEntity todo){
        service.atualizarStatus(todo);
    }

    @GetMapping("{id}")
    public TodoEntity buscar(@PathVariable("id") Integer id){
        return service.buscarPorId(id);
    }
}
