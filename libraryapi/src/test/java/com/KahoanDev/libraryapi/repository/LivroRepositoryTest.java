package com.KahoanDev.libraryapi.repository;

import com.KahoanDev.libraryapi.model.Autor;
import com.KahoanDev.libraryapi.model.Livro;
import com.KahoanDev.libraryapi.model.enums.GeneroLivro;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@SpringBootTest
class LivroRepositoryTest {

    @Autowired
    LivroRepository livroRepository;

    @Autowired
    AutorRepository autorRepository;

    @Test
    void salvarTest(){
        Livro livro = new Livro();
        livro.setIsbn("98711-00654");
        livro.setPreco(BigDecimal.valueOf(100));
        livro.setGenero(GeneroLivro.BIOGRAFIA);
        livro.setTitulo("UFO");
        livro.setDataPublicacao(LocalDate.of(1980, 1, 1));

        Autor autor = autorRepository.findById(UUID.fromString("1792998c-8251-4634-8fde-c4b420d226bf")).orElse(null);

        livro.setAutor(autor);

        livroRepository.save(livro);
    }

    @Test
    void salvarCacadeTest(){
        Livro livro = new Livro();
        livro.setIsbn("98711-00654");
        livro.setPreco(BigDecimal.valueOf(100));
        livro.setGenero(GeneroLivro.CIENCIA);
        livro.setTitulo("Outro Livro");
        livro.setDataPublicacao(LocalDate.of(1988, 1, 1));

        Autor autor = new Autor();
        autor.setNome("João");
        autor.setNacionalidade("Brasileiro");
        autor.setDataNascimento(LocalDate.of(1938, 8, 15));

        livro.setAutor(autor);

        livroRepository.save(livro);
    }

    @Test
    void salvarAutorELivroTest(){
        Livro livro = new Livro();
        livro.setIsbn("98711-00654");
        livro.setPreco(BigDecimal.valueOf(100));
        livro.setGenero(GeneroLivro.ROMANCE);
        livro.setTitulo("Terceiro Livro");
        livro.setDataPublicacao(LocalDate.of(2000, 1, 1));

        Autor autor = new Autor();
        autor.setNome("José");
        autor.setNacionalidade("Brasileiro");
        autor.setDataNascimento(LocalDate.of(1938, 8, 15));

        autorRepository.save(autor);

        livro.setAutor(autor);

        livroRepository.save(livro);
    }

    @Test
    void atualizarAutorDoLivro(){
        UUID id = UUID.fromString("53ef7be5-579a-47b7-9d1d-14b6361fac55");
        var livroParaAtualizar = livroRepository.findById(id).orElse(null);

        UUID autorId = UUID.fromString("a83e944e-9cba-4177-aded-1d7f710e9ff5");
        Autor autor = autorRepository.findById(autorId).orElse(null);

        livroParaAtualizar.setAutor(autor);

        livroRepository.save(livroParaAtualizar);
    }

    @Test
    @Transactional
    void buscarLivrosTest(){
        UUID id = UUID.fromString("53ef7be5-579a-47b7-9d1d-14b6361fac55");
        Livro livro = livroRepository.findById(id).orElse(null);
        System.out.println("Livro: ");
        System.out.println(livro.getTitulo());
        System.out.println("Autor: ");
        System.out.println(livro.getAutor().getNome());

    }

    @Test
    void pesquisaPorTituloTest(){
        List<Livro> lista = livroRepository.findByTitulo("O amor daquele que amou de mais");

        lista.forEach(System.out::println);
    }

    @Test
    void pesquisaPorISBNTest(){
        List<Livro> lista = livroRepository.findByIsbn("96611-15724");

        lista.forEach(System.out::println);
    }

    @Test
    void listaTodosTeste(){
        List<Livro> lista = livroRepository.listarTodos();

        lista.forEach(System.out::println);
    }

    @Test
    void listarPorGeneroQueryParam(){
        List<Livro> lista = livroRepository.findByGenero(GeneroLivro.MISTERIO, "dataPublicacao");

        lista.forEach(System.out::println);
    }

    @Test
    void listarPorGeneroPositionalParam(){
        List<Livro> lista = livroRepository.findByGeneroPositionalParam(GeneroLivro.MISTERIO, "dataPublicacao");

        lista.forEach(System.out::println);
    }

    @Test
    void deleteByGeneroTest(){
        livroRepository.deleteByGenero(GeneroLivro.CIENCIA);
    }

    @Test
    void updateDataPublicacaoTest(){
        livroRepository.updateDataPublicacao(LocalDate.of(2000,1,1));
    }
}