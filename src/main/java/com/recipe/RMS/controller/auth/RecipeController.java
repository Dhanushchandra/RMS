package com.recipe.RMS.controller.auth;

import com.recipe.RMS.dto.recipe.RecipeDto;
import com.recipe.RMS.model.Recipe;
import com.recipe.RMS.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Set;
import java.util.UUID;
@RequestMapping("/v1/recipe")
@RestController
public class RecipeController {

    @Autowired
    private RecipeService recipeService;

    @PostMapping
    public ResponseEntity<RecipeDto> createRecipe(
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam("userId") UUID userId,
            @RequestParam(value = "categories", required = false) Set<UUID> categoryIds,
            @RequestParam(value = "image", required = false) MultipartFile image) throws IOException {

        RecipeDto recipeDTO = recipeService.createRecipe(name, description, userId, categoryIds, image);

        if (image != null && !image.isEmpty()) {
            String base64Image = Base64.getEncoder().encodeToString(image.getBytes());
            recipeDTO.setBase64Image(base64Image);
        }


        return new ResponseEntity<>(recipeDTO, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecipeDto> getRecipe(@PathVariable UUID id) {
        RecipeDto recipeDTO = recipeService.getRecipe(id);
        return ResponseEntity.ok(recipeDTO);
    }

    @GetMapping
    public ResponseEntity<List<RecipeDto>> getAllRecipes() {
        List<RecipeDto> recipeDTOs = recipeService.getAllRecipes();
        return ResponseEntity.ok(recipeDTOs);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RecipeDto> updateRecipe(
            @PathVariable UUID id,
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam(value = "categories", required = false) Set<UUID> categoryIds,
            @RequestParam(value = "image", required = false) MultipartFile image) throws IOException {

        RecipeDto updatedRecipeDTO = recipeService.updateRecipe(id, name, description, categoryIds, image);
        return ResponseEntity.ok(updatedRecipeDTO);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecipe(@PathVariable UUID id) {
        recipeService.deleteRecipe(id);
        return ResponseEntity.noContent().build();
    }
}
