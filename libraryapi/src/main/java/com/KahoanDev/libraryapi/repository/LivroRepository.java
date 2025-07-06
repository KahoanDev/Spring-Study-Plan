package com.KahoanDev.libraryapi.repository;

import com.KahoanDev.libraryapi.model.Autor;
import com.KahoanDev.libraryapi.model.Livro;
import com.KahoanDev.libraryapi.model.enums.GeneroLivro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface LivroRepository extends JpaRepository<Livro, UUID>, JpaSpecificationExecutor<Livro> {

    boolean existsByAutor(Autor autor);
    List<Livro> findByAutor(Autor autor);
    List<Livro> findByTitulo(String titulo);
    List<Livro> findByIsbn(String isbn);

    // JPQL -> Referencia as entidades e as propriedades
    @Query("SELECT l FROM Livro as l ORDER BY l.titulo")
    List<Livro> listarTodos();


    // Named Parameters -> Parametros Nomeados
    @Query("""
            SELECT l
            FROM Livro l
            WHERE l.genero = :genero
            ORDER BY :paramOrdenacao
            """)
    List<Livro> findByGenero(@Param("genero") GeneroLivro generoLivro, @Param("paramOrdenacao") String nomePropriedade);

    // Positional Parameters -> Parametros Posicionais
    @Query("""
            SELECT l
            FROM Livro l
            WHERE l.genero = ?1
            ORDER BY ?2
            """)
    List<Livro> findByGeneroPositionalParam( GeneroLivro generoLivro, String nomePropriedade);

    @Modifying
    @Transactional
    @Query("DELETE FROM Livro WHERE genero = ?1")
    void deleteByGenero(GeneroLivro genero);

    @Modifying
    @Transactional
    @Query("UPDATE Livro SET dataPublicacao = ?1")
    void updateDataPublicacao(LocalDate novaData);
}
