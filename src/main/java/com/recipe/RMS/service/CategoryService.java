package com.recipe.RMS.service;

import com.recipe.RMS.dto.category.CategoryDTO;
import com.recipe.RMS.exceptions.CustomException;
import com.recipe.RMS.exceptions.ResourceNotFoundException;
import com.recipe.RMS.model.Category;
import com.recipe.RMS.repository.CategoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepo categoryRepo;

    public CategoryDTO createCategory(Category category) {
        try {
            Category savedCategory = categoryRepo.save(category);
            return mapToDTO(savedCategory);
        } catch (Exception e) {
            throw new CustomException("Error creating category", e);
        }
    }

    public CategoryDTO getCategory(UUID id) {
        Category category = categoryRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));
        return mapToDTO(category);
    }

    public List<CategoryDTO> getAllCategories() {
        List<Category> categories = categoryRepo.findAll();
        return categories.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public CategoryDTO updateCategory(UUID id, Category updatedCategory) {
        Category existingCategory = categoryRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));

        existingCategory.setName(updatedCategory.getName());
        Category updatedCategoryEntity = categoryRepo.save(existingCategory);
        return mapToDTO(updatedCategoryEntity);
    }

    public void deleteCategory(UUID id) {
        categoryRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));
        categoryRepo.deleteById(id);
    }

    private CategoryDTO mapToDTO(Category category) {
        return new CategoryDTO(category.getId(), category.getName());
    }
}

