package com.example.springbootintro.repository;

import com.example.springbootintro.model.Book;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BookRepository extends JpaRepository<Book, Long> {
    @Query("FROM Book b LEFT JOIN FETCH b.categories c WHERE c.id = :id")
    List<Book> findAllByCategoriesId(@Param("id") Long categoryId, Pageable pageable);
}
