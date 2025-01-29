package com.KahoanOli.arquiteturaspring.todos;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TodoService {

    private TodoRepository repository;
    private TodoValidator validator;
    private MailSender mailSender;

    public TodoEntity salvar(TodoEntity newTodo){
        validator.validar(newTodo);
        return repository.save(newTodo);
    }

    public void atualizarStatus(TodoEntity todo){
        repository.save(todo);
        String status = todo.getConcluido() == Boolean.TRUE ? "Concluído" : "Não concluído";
        mailSender.enviar("Todo " + todo.getDescricao() + " foi atualizado para " + status);
    }

    public TodoEntity buscarPorId(Integer id){
        return repository.findById(id).orElse(null);
    }
}
