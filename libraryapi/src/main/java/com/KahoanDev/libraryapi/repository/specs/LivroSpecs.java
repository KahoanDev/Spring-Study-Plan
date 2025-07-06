package com.KahoanDev.libraryapi.repository.specs;

import com.KahoanDev.libraryapi.model.Livro;
import org.springframework.data.jpa.domain.Specification;

public class LivroSpecs {

    public static Specification<Livro> isbnEqual(String isbn){
        return null;
    }
}
