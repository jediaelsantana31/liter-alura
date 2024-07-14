package com.challenge.literalura.repository;

import com.challenge.literalura.model.Author;
import com.challenge.literalura.model.Book;
import com.challenge.literalura.model.Languages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

    @Query("SELECT a FROM Book b JOIN b.authors a")
    List<Author> getAuthorsInfo();

    @Query("SELECT a FROM Book b JOIN b.authors a WHERE a.birthYear > :date")
    List<Author> getAuthorsAliveAfter(int date);

    List<Book> findByLanguages(Languages languages);
}