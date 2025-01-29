package com.KahoanOli.arquiteturaspring;

import com.KahoanOli.arquiteturaspring.todos.*;
import jakarta.persistence.EntityManager;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.sql.Connection;

public class ExemploInjecaoDependencia {
    public static void main(String[] args) throws Exception{
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl("url");
        dataSource.setUsername("user");
        dataSource.setPassword("password");

        Connection connection = dataSource.getConnection();

        EntityManager manager = null;

        TodoRepository repository = null; //new SimpleJpaRepository<TodoEntity, Integer>();
        TodoValidator validator = new TodoValidator(repository);
        MailSender mailSender = new MailSender();

        TodoService service = new TodoService(repository, validator, mailSender);
    }
}
