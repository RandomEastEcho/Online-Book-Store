package com.example.springbootintro.repository;

import com.example.springbootintro.model.Book;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BookRepository extends JpaRepository<Book, Long> {
    @Query("FROM Book b LEFT JOIN b.categories c WHERE c.id = :categoryId")
    List<Book> findAllByCategoriesId(Long categoryId, Pageable pageable);
}
