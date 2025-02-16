package com.recipe.RMS.controller.noauth;

import com.recipe.RMS.dto.category.CategoryDTO;
import com.recipe.RMS.dto.recipe.RecipeDto;
import com.recipe.RMS.service.NoAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/noauth/v1")
public class NoAuthController {

    @Autowired
    private NoAuthService recipeService;

    @GetMapping("/categories")
    public ResponseEntity<List<CategoryDTO>> getAllCategories() {
        List<CategoryDTO> categories = recipeService.getAllCategories();
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/recipes")
    public ResponseEntity<Page<RecipeDto>> getAllRecipes(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<RecipeDto> recipes = recipeService.getAllRecipes(PageRequest.of(page, size));
        return ResponseEntity.ok(recipes);
    }

    @GetMapping("/recipes/{id}")
    public ResponseEntity<RecipeDto> getRecipe(@PathVariable UUID id) {
        RecipeDto recipeDTO = recipeService.getRecipe(id);
        return ResponseEntity.ok(recipeDTO);
    }

    @GetMapping("/recipes/by-category")
    public ResponseEntity<Page<RecipeDto>> getRecipesByCategory(
            @RequestParam List<UUID> categoryIds,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<RecipeDto> recipes = recipeService.getRecipesByCategories(categoryIds, PageRequest.of(page, size));
        return ResponseEntity.ok(recipes);
    }

    @GetMapping("/recipes/search")
    public ResponseEntity<Page<RecipeDto>> searchRecipes(
            @RequestParam String query,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<RecipeDto> recipes = recipeService.searchRecipes(query, PageRequest.of(page, size));
        return ResponseEntity.ok(recipes);
    }
}

