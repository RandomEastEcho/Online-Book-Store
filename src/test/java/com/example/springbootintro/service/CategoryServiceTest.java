package com.example.springbootintro.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.springbootintro.dto.request.CategoryRequestDto;
import com.example.springbootintro.dto.response.CategoryResponseDto;
import com.example.springbootintro.exception.EntityNotFoundException;
import com.example.springbootintro.mapper.CategoryMapper;
import com.example.springbootintro.model.Category;
import com.example.springbootintro.repository.CategoryRepository;
import com.example.springbootintro.service.impl.CategoryServiceImpl;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {
    private Category testCategory;
    private CategoryRequestDto testRequestDto;
    private CategoryResponseDto expectedResponse;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private CategoryMapper categoryMapper;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    @BeforeEach
    public void init() {
        testCategory = new Category();
        testCategory.setId(1L);
        testCategory.setName("testName");
        testCategory.setDescription("testDescription");
        testRequestDto = new CategoryRequestDto();
        expectedResponse = new CategoryResponseDto();
    }

    @Test
    void save_saveCategory_ok() {
        when(categoryMapper.toModel(testRequestDto)).thenReturn(testCategory);
        when(categoryRepository.save(testCategory)).thenReturn(testCategory);
        when(categoryMapper.toDto(testCategory)).thenReturn(expectedResponse);

        CategoryResponseDto actualResponse = categoryService.save(testRequestDto);

        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void findAll_findAllCategories_ok() {
        Pageable pageable = mock(Pageable.class);
        List<Category> categories = Arrays.asList(new Category(), new Category());
        Page<Category> categoryPage = new PageImpl<>(categories);
        List<CategoryResponseDto> expectedResponse = Arrays.asList(new CategoryResponseDto(),
                new CategoryResponseDto());

        when(categoryRepository.findAll(pageable)).thenReturn(categoryPage);
        when(categoryMapper.toDto(any())).thenReturn(new CategoryResponseDto());

        List<CategoryResponseDto> actualResponse = categoryService.findAll(pageable);

        assertEquals(expectedResponse.size(), actualResponse.size());
    }

    @Test
    void getById_getCategoryById_ok() {
        when(categoryRepository.findById(testCategory.getId()))
                .thenReturn(Optional.of(testCategory));
        when(categoryMapper.toDto(testCategory)).thenReturn(expectedResponse);

        CategoryResponseDto actualResponse = categoryService.getById(testCategory.getId());

        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void getById_getCategoryByNonExistingId_notOk() {
        Long nonExistingId = 2L;

        when(categoryRepository.findById(nonExistingId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> categoryService.getById(nonExistingId));
    }

    @Test
    void deleteById_deleteCategoryById_ok() {
        categoryService.deleteById(testCategory.getId());

        verify(categoryRepository, times(1)).deleteById(testCategory.getId());
    }

    @Test
    void update_updateCategory_ok() {
        Category updatedCategory = new Category();

        when(categoryRepository.findById(testCategory.getId()))
                .thenReturn(Optional.of(testCategory));
        when(categoryRepository.save(any())).thenReturn(updatedCategory);
        when(categoryMapper.toDto(updatedCategory)).thenReturn(expectedResponse);

        CategoryResponseDto actualResponse =
                categoryService.update(testCategory.getId(), testRequestDto);

        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void update_updateCategoryByNotExistingId_notOk() {
        Long nonExistingId = 2L;

        when(categoryRepository.findById(nonExistingId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> categoryService.getById(nonExistingId));
        verify(categoryRepository, times(1)).findById(nonExistingId);
    }
}
