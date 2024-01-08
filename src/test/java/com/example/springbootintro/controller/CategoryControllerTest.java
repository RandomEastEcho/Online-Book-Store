package com.example.springbootintro.controller;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.springbootintro.dto.request.CategoryRequestDto;
import com.example.springbootintro.dto.response.BookWithoutCategoryResponseDto;
import com.example.springbootintro.dto.response.CategoryResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;
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
public class CategoryControllerTest {
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
    @Sql(scripts = "classpath:database/category/add-new-category-to-table.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:database/category/remove-categories-from-table.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void getCategoryById_getCategoryById_ok() throws Exception {
        Long categoryId = 1L;
        mockMvc.perform(get("/api/categories/" + categoryId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(categoryId))
                .andExpect(jsonPath("$.name").value("testName"));
    }

    @Test
    void getCategoryById_getCategoryWithIdUnauthorizedUser_notOk() throws Exception {
        Long categoryId = 1L;
        mockMvc.perform(
                        get("/api/categories/" + categoryId)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "admin", authorities = "ADMIN")
    @Sql(scripts = "classpath:database/category/remove-categories-from-table.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void createCategory_createNewCategory_ok() throws Exception {
        CategoryRequestDto testCategory = createCategory();
        String jsonObject = objectMapper.writeValueAsString(testCategory);
        mockMvc.perform(post("/api/categories/")
                        .content(jsonObject)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value(testCategory.getName()))
                .andExpect(jsonPath("$.description").value(testCategory.getDescription()));
    }

    @Test
    void createCategory_createNewCategoryUnauthorizedUser_notOk() throws Exception {
        CategoryRequestDto testCategory = createCategory();
        String jsonObject = objectMapper.writeValueAsString(testCategory);
        mockMvc.perform(post("/api/categories/")
                        .content(jsonObject)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "admin", authorities = "ADMIN")
    @Sql(scripts = "classpath:database/category/add-new-category-to-table.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:database/category/remove-categories-from-table.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void deleteCategoryById_deleteCategoryById_ok() throws Exception {
        Long categoryId = 1L;
        mockMvc.perform(delete("/api/categories/" + categoryId))
                .andExpect(status().isOk());

        MvcResult result = mockMvc.perform(
                        get("/api/categories/")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        String contentAsString = result.getResponse().getContentAsString();
        List<CategoryResponseDto> actual = List.of(
                objectMapper.readValue(contentAsString, CategoryResponseDto[].class));

        Assertions.assertEquals(actual.size(), 0);
    }

    @Test
    void deleteCategoryById_deleteCategoryByIdUnauthorizedUser_notOk() throws Exception {
        Long categoryId = 1L;
        mockMvc.perform(delete("/api/categories/" + categoryId))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "admin", authorities = "ADMIN")
    @Sql(scripts = "classpath:database/category/add-new-category-to-table.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:database/category/remove-categories-from-table.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void updateCategory_updateCategoryById_ok() throws Exception {
        Long categoryId = 1L;
        CategoryRequestDto testCategory = createCategory();
        String jsonObject = objectMapper.writeValueAsString(testCategory);

        mockMvc.perform(put("/api/categories/" + categoryId)
                        .content(jsonObject)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value(testCategory.getName()))
                .andExpect(jsonPath("$.description").value(testCategory.getDescription()));
    }

    @Test
    void updateCategory_updateCategoryUnauthorizedUser_notOk() throws Exception {
        Long categoryId = 1L;
        CategoryRequestDto testCategory = createCategory();
        String jsonObject = objectMapper.writeValueAsString(testCategory);
        mockMvc.perform(put("/api/categories/" + categoryId)
                        .content(jsonObject)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "user", authorities = "USER")
    @Sql(scripts = "classpath:database/book/add-new-book-to-table.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:database/book/remove-books-from-table.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void getBooksByCategoryId_getAllBooksByCategoryId_ok() throws Exception {
        Long categoryId = 1L;
        MvcResult result = mockMvc.perform(get("/api/categories/" + categoryId + "/books"))
                .andExpect(status().isOk())
                .andReturn();

        List<BookWithoutCategoryResponseDto> responseDtoList = List.of(
                objectMapper.readValue(result.getResponse().getContentAsString(),
                        BookWithoutCategoryResponseDto[].class));

        Assertions.assertEquals(1, responseDtoList.size());
    }

    @Test
    void getBooksByCategoryId_getAllBooksByCategoryIdUnauthorizedUser_ok() throws Exception {
        Long categoryId = 1L;
        mockMvc.perform(get("/api/categories/" + categoryId + "/books"))
                .andExpect(status().isUnauthorized());
    }

    private CategoryRequestDto createCategory() {
        CategoryRequestDto newCategory = new CategoryRequestDto();
        newCategory.setName("testName");
        newCategory.setDescription("testDescription");
        return newCategory;
    }
}
