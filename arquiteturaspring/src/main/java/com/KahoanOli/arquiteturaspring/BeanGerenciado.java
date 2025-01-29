package com.KahoanOli.arquiteturaspring;

import com.KahoanOli.arquiteturaspring.todos.TodoEntity;
import com.KahoanOli.arquiteturaspring.todos.TodoValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Lazy
@Component
@Scope("singleton")
//@Scope("request")
//@Scope("session")
//@Scope("application")
public class BeanGerenciado {

    @Autowired
    private TodoValidator validator;

    public void utilizar(){
        var todo = new TodoEntity();
        validator.validar(todo);
    }
}
