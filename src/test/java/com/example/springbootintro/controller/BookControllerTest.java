package com.example.springbootintro.controller;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.springbootintro.dto.request.BookRequestDto;
import com.example.springbootintro.dto.response.BookResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BookControllerTest {
    protected static MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeAll
    static void setUp(@Autowired WebApplicationContext applicationContext) {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(applicationContext)
                .apply(springSecurity())
                .build();
    }

    @Test
    @WithMockUser(username = "user", authorities = "USER")
    @Sql(scripts = "classpath:database/book/add-new-book-to-table.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:database/book/remove-books-from-table.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void getAll_getAllBooks_ok() throws Exception {
        MvcResult result = mockMvc.perform(
                get("/api/books/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        String contentAsString = result.getResponse().getContentAsString();
        List<BookResponseDto> actual =
                List.of(objectMapper.readValue(contentAsString, BookResponseDto[].class));
        Assertions.assertEquals(1, actual.size());
    }

    @Test
    @WithMockUser(username = "user", authorities = "USER")
    @Sql(scripts = "classpath:database/book/add-new-book-to-table.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:database/book/remove-books-from-table.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void getAll_getAllBooksWithParamCheck_ok() throws Exception {
        MvcResult result = mockMvc.perform(
                        get("/api/books/")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        String contentAsString = result.getResponse().getContentAsString();
        List<BookResponseDto> actual = List.of(
                objectMapper.readValue(contentAsString, BookResponseDto[].class));
        for (BookResponseDto dto : actual) {
            Assertions.assertNotNull(dto);
            Assertions.assertEquals(dto.getId(), 1L);
            Assertions.assertEquals(dto.getAuthor(), "testAuthor");
        }
    }

    @Test
    void getAll_getAllBooksWithUnauthorizedUser_notOk() throws Exception {
        MvcResult result = mockMvc.perform(
                        get("/api/books/")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized())
                .andReturn();
    }

    @Test
    @WithMockUser(username = "user", authorities = "USER")
    @Sql(scripts = "classpath:database/book/add-new-book-to-table.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:database/book/remove-books-from-table.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void getBookById_getBookById_ok() throws Exception {
        Long bookId = 1L;
        mockMvc.perform(get("/api/books/" + bookId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(bookId))
                .andExpect(jsonPath("$.title").value("testTitle"));
    }

    @Test
    void getBookById_getBooksWithIdUnauthorizedUser_notOk() throws Exception {
        Long bookId = 1L;
        MvcResult result = mockMvc.perform(
                        get("/api/books/" + bookId)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized())
                .andReturn();
    }

    @Test
    @WithMockUser(username = "admin", authorities = "ADMIN")
    @Sql(scripts = "classpath:database/book/remove-books-from-table.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void createBook_createNewBook_ok() throws Exception {
        BookRequestDto testBook = createBook();
        String jsonObject = objectMapper.writeValueAsString(testBook);
        mockMvc.perform(post("/api/books/")
                .content(jsonObject)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.title").value(testBook.getTitle()))
                .andExpect(jsonPath("$.author").value(testBook.getAuthor()))
                .andExpect(jsonPath("$.isbn").value(testBook.getIsbn()))
                .andExpect(jsonPath("$.description").value(testBook.getDescription()));
    }

    @Test
    void createBook_createNewBookUnauthorizedUser_notOk() throws Exception {
        BookRequestDto testBook = createBook();
        String jsonObject = objectMapper.writeValueAsString(testBook);
        mockMvc.perform(post("/api/books/")
                        .content(jsonObject)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "admin", authorities = "ADMIN")
    @Sql(scripts = "classpath:database/book/add-new-book-to-table.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:database/book/remove-books-from-table.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void deleteById_deleteBookById_ok() throws Exception {
        Long bookId = 1L;
        mockMvc.perform(delete("/api/books/" + bookId))
                .andExpect(status().isOk());

        MvcResult result = mockMvc.perform(
                        get("/api/books/")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        String contentAsString = result.getResponse().getContentAsString();
        List<BookResponseDto> actual = List.of(
                objectMapper.readValue(contentAsString, BookResponseDto[].class));

        Assertions.assertEquals(actual.size(), 0);
    }

    @Test
    void deleteById_deleteBookByIdUnauthorizedUser_notOk() throws Exception {
        Long bookId = 1L;
        mockMvc.perform(delete("/api/books/" + bookId))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "admin", authorities = "ADMIN")
    @Sql(scripts = "classpath:database/book/add-new-book-to-table.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:database/book/remove-books-from-table.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void update_updateBookById_ok() throws Exception {
        Long bookId = 1L;
        BookRequestDto testBook = createBook();
        String jsonObject = objectMapper.writeValueAsString(testBook);

        mockMvc.perform(put("/api/books/" + bookId)
                        .content(jsonObject)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.title").value(testBook.getTitle()))
                .andExpect(jsonPath("$.author").value(testBook.getAuthor()))
                .andExpect(jsonPath("$.isbn").value(testBook.getIsbn()))
                .andExpect(jsonPath("$.description").value(testBook.getDescription()));
    }

    @Test
    void update_updateBookUnauthorizedUser_notOk() throws Exception {
        Long bookId = 1L;
        BookRequestDto testBook = createBook();
        String jsonObject = objectMapper.writeValueAsString(testBook);
        mockMvc.perform(put("/api/books/" + bookId)
                        .content(jsonObject)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    private BookRequestDto createBook() {
        BookRequestDto newBook = new BookRequestDto();
        newBook.setTitle("testTitle");
        newBook.setAuthor("testTitle");
        newBook.setPrice(BigDecimal.ONE);
        newBook.setIsbn("testIsbn");
        newBook.setCoverImage("testImage");
        newBook.setDescription("testDescription");
        return newBook;
    }
}
