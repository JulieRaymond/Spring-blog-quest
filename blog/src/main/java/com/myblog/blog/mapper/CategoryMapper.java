package com.myblog.blog.mapper;

import com.myblog.blog.dto.CategoryDTO;
import com.myblog.blog.model.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    // Convertit une entité Category en un CategoryDTO
    public CategoryDTO convertToDTO(Category category) {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(category.getId());
        categoryDTO.setName(category.getName());
        return categoryDTO;
    }

    // Convertit un CategoryDTO en une entité Category
    public Category convertToEntity(CategoryDTO categoryDTO) {
        Category category = new Category();
        category.setId(categoryDTO.getId());
        category.setName(categoryDTO.getName());
        return category;
    }
}
