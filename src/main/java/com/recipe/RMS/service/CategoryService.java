package com.recipe.RMS.service;

import com.recipe.RMS.exceptions.ResourceNotFoundException;
import com.recipe.RMS.model.Category;
import com.recipe.RMS.repository.CategoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;
import java.util.UUID;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepo categoryRepo;

    public Category createCategory(Category category) {
        return categoryRepo.save(category);
    }

    public Category getCategory(UUID id) {
        return categoryRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));
    }

    public List<Category> getAllCategories() {
        return categoryRepo.findAll();
    }

    public Category updateCategory(UUID id, Category updatedCategory) {
        Category existingCategory = getCategory(id);
        existingCategory.setName(updatedCategory.getName());
        return categoryRepo.save(existingCategory);
    }

    public void deleteCategory(UUID id) {
        categoryRepo.deleteById(id);
    }
}

