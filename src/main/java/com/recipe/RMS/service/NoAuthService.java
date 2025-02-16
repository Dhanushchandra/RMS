package com.recipe.RMS.service;

import com.recipe.RMS.dto.category.CategoryDTO;
import com.recipe.RMS.dto.recipe.RecipeDto;
import com.recipe.RMS.dto.user.UserDto;
import com.recipe.RMS.exceptions.CustomException;
import com.recipe.RMS.model.Category;
import com.recipe.RMS.model.Recipe;
import com.recipe.RMS.repository.CategoryRepo;
import com.recipe.RMS.repository.RecipeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class NoAuthService {
    @Autowired
    private RecipeRepo recipeRepository;

    @Autowired
    private CategoryRepo categoryRepository;

    public List<CategoryDTO> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream()
                .map(category -> new CategoryDTO(category.getId(), category.getName()))
                .collect(Collectors.toList());
    }

    public Page<RecipeDto> getAllRecipes(Pageable pageable) {
        Page<Recipe> recipes = recipeRepository.findAll(pageable);
        return recipes.map(this::mapToDTO);
    }

    public RecipeDto getRecipe(UUID id) {
        Recipe recipe = recipeRepository.findById(id)
                .orElseThrow(() -> new CustomException("Recipe not found for id: " + id));
        return mapToDTO(recipe);
    }

    public Page<RecipeDto> getRecipesByCategories(List<UUID> categoryIds, Pageable pageable) {
        List<Category> categories = categoryRepository.findAllById(categoryIds);
        if (categories.isEmpty()) {
            throw new CustomException("Categories not found for ids: " + categoryIds);
        }
        Page<Recipe> recipes = recipeRepository.findDistinctByCategoriesIn(categories, pageable);
        return recipes.map(this::mapToDTO);
    }

    public Page<RecipeDto> searchRecipes(String query, Pageable pageable) {
        Page<Recipe> recipes = recipeRepository.findByNameContainingIgnoreCase(query, pageable);
        return recipes.map(this::mapToDTO);
    }

    private RecipeDto mapToDTO(Recipe recipe) {
        RecipeDto dto = new RecipeDto();
        dto.setId(recipe.getId());
        dto.setName(recipe.getName());
        dto.setDescription(recipe.getDescription());
        dto.setImagePath(recipe.getImagePath());
        dto.setCategories(recipe.getCategories().stream().map(Category::getName).collect(Collectors.toSet()));

        if (recipe.getImagePath() != null) {
            try {
                byte[] imageBytes = Files.readAllBytes(Paths.get(recipe.getImagePath()));
                String base64Image = Base64.getEncoder().encodeToString(imageBytes);
                dto.setBase64Image(base64Image);
            } catch (IOException e) {
                throw new CustomException("Error reading image from disk", e);
            }
        }

        // Map the User to UserDTO
        UserDto userDTO = new UserDto();
        userDTO.setId(recipe.getUser().getId().toString());
        userDTO.setUsername(recipe.getUser().getUsername());
        userDTO.setEmail(recipe.getUser().getEmail());

        dto.setUser(userDTO);

        return dto;
    }

}
