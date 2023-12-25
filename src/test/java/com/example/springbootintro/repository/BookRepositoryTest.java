package com.example.springbootintro.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.example.springbootintro.model.Book;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.jdbc.Sql;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class BookRepositoryTest {
    @Autowired
    private BookRepository bookRepository;

    @Test
    @Sql(scripts = "classpath:database/book/add-new-book-to-table.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:database/book/remove-books-from-table.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findAllByCategoriesId_FindAllBooksByCategoriesId_ok() {
        List<Book> actual = bookRepository.findAllByCategoriesId(1L, PageRequest.of(0, 10));
        assertEquals(1, actual.size());
        assertEquals(bookRepository.findById(1L).get(), Optional.of(actual.get(0)).get());
    }
}
