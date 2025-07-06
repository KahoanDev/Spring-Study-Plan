package com.KahoanDev.libraryapi.repository;

import com.KahoanDev.libraryapi.model.Autor;
import com.KahoanDev.libraryapi.model.Livro;
import com.KahoanDev.libraryapi.model.enums.GeneroLivro;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
class AutorRepositoryTest {

    @Autowired
    AutorRepository autorRepository;

    @Autowired
    LivroRepository livroRepository;

    @Test
    void salvarTeste(){
        Autor autor = new Autor();
        autor.setNome("Jalam Bipal");
        autor.setNacionalidade("Turco");
        autor.setDataNascimento(LocalDate.of(1930, 8, 15));

        var autorSalvo = autorRepository.save(autor);
        System.out.println("Autor Salvo: " + autorSalvo);
    }

    @Test
    void AtualizarTeste(){
        var id = UUID.fromString("6a5bb2e4-062e-4256-8603-1e0dcd0df522");

        Optional<Autor> autor = autorRepository.findById(id);

        if (autor.isPresent()){
            Autor autorEncontrado = autor.get();
            System.out.println("Dados do Autor:");
            System.out.println(autorEncontrado);

            autorEncontrado.setNome("Cleiso Romario");

            autorRepository.save(autorEncontrado);
        }
    }

    @Test
    void listarTest(){
        List<Autor> lista = autorRepository.findAll();
        lista.forEach(System.out::println);
    }

    @Test
    void countTest(){
        System.out.println("Contagem de autores: " + autorRepository.count());
    }

    @Test
    void deletePorIdTest(){
        var id = UUID.fromString("6a5bb2e4-062e-4256-8603-1e0dcd0df522");
        autorRepository.deleteById(id);
    }

    @Test
    void deleteTest(){
        var id = UUID.fromString("87af94e6-6b43-40ee-b0d8-0086f7cd94c3");
        var jalamBipal = autorRepository.findById(id).get();
        autorRepository.delete(jalamBipal);
    }

    @Test
    void salvarAutorComLivrosTest(){
        Autor autor = new Autor();
        autor.setNome("Salamaleko");
        autor.setNacionalidade("Arabe");
        autor.setDataNascimento(LocalDate.of(1971, 2, 12));

        Livro livro = new Livro();
        livro.setIsbn("96611-98654");
        livro.setPreco(BigDecimal.valueOf(139));
        livro.setGenero(GeneroLivro.ROMANCE);
        livro.setTitulo("O amor daquele que amou de mais");
        livro.setDataPublicacao(LocalDate.of(1999, 1, 1));
        livro.setAutor(autor);

        Livro livro2 = new Livro();
        livro2.setIsbn("96611-15724");
        livro2.setPreco(BigDecimal.valueOf(139));
        livro2.setGenero(GeneroLivro.MISTERIO);
        livro2.setTitulo("O grande mistério");
        livro2.setDataPublicacao(LocalDate.of(2002, 9, 24));
        livro2.setAutor(autor);

        autor.setLivros(new ArrayList<>());
        autor.getLivros().add(livro);
        autor.getLivros().add(livro2);

        autorRepository.save(autor);
        //livroRepository.saveAll(autor.getLivros());
    }
    @Test
    void listarLivrosAutor(){
        var id = UUID.fromString("fa516c45-7784-4c68-afb0-38962d2a514a");
        var autor = autorRepository.findById(id).get();

        List<Livro> livrosList = livroRepository.findByAutor(autor);
        autor.setLivros(livrosList);

        autor.getLivros().forEach(System.out::println);
    }

}
