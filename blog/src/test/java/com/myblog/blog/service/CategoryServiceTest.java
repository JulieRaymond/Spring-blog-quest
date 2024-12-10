package com.myblog.blog.service;

import com.myblog.blog.dto.CategoryDTO;
import com.myblog.blog.mapper.CategoryMapper;
import com.myblog.blog.model.Category;
import com.myblog.blog.repository.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)  // Utilise MockitoExtension pour activer les mocks
class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private CategoryMapper categoryMapper;

    @InjectMocks
    private CategoryService categoryService;

    @Test
    void testGetAllCategories() {
        // Arrange
        Category category1 = new Category();
        category1.setName("Category 1");

        Category category2 = new Category();
        category2.setName("Category 2");

        CategoryDTO categoryDTO1 = new CategoryDTO();
        categoryDTO1.setName("Category 1");

        CategoryDTO categoryDTO2 = new CategoryDTO();
        categoryDTO2.setName("Category 2");

        // Simuler la réponse du repository
        when(categoryRepository.findAll()).thenReturn(List.of(category1, category2));
        // Simuler la conversion des entités en DTOs
        when(categoryMapper.convertToDTO(category1)).thenReturn(categoryDTO1);
        when(categoryMapper.convertToDTO(category2)).thenReturn(categoryDTO2);

        // Act
        List<CategoryDTO> categories = categoryService.getAllCategories();

        // Assert
        assertThat(categories).hasSize(2);
        assertThat(categories.get(0).getName()).isEqualTo("Category 1");
        assertThat(categories.get(1).getName()).isEqualTo("Category 2");

        verify(categoryRepository, times(1)).findAll();  // Vérifier l'appel à findAll()
        verify(categoryMapper, times(1)).convertToDTO(category1);  // Vérifier la conversion en DTO
        verify(categoryMapper, times(1)).convertToDTO(category2);  // Vérifier la conversion en DTO
    }

    @Test
    void testGetCategoryById_CategoryExists() {
        // Arrange
        Category category = new Category();
        category.setName("Category 1");

        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setName("Category 1");

        // Simuler la réponse du repository
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        // Simuler la conversion en DTO
        when(categoryMapper.convertToDTO(category)).thenReturn(categoryDTO);

        // Act
        CategoryDTO result = categoryService.getCategoryById(1L);

        // Assert
        assertThat(result.getName()).isEqualTo("Category 1");

        verify(categoryRepository, times(1)).findById(1L);  // Vérifier l'appel à findById
        verify(categoryMapper, times(1)).convertToDTO(category);  // Vérifier la conversion en DTO
    }

    @Test
    void testGetCategoryById_CategoryNotFound() {
        // Arrange
        when(categoryRepository.findById(99L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThat(categoryService.getCategoryById(99L)).isNull();

        verify(categoryRepository, times(1)).findById(99L);  // Vérifier l'appel à findById
    }
}
