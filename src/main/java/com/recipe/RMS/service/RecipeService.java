package com.recipe.RMS.service;

import com.recipe.RMS.dto.recipe.RecipeDto;
import com.recipe.RMS.dto.user.UserDto;
import com.recipe.RMS.exceptions.CustomException;
import com.recipe.RMS.model.Category;
import com.recipe.RMS.model.Recipe;
import com.recipe.RMS.model.User;
import com.recipe.RMS.repository.CategoryRepo;
import com.recipe.RMS.repository.RecipeRepo;
import com.recipe.RMS.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class RecipeService {

    @Autowired
    private RecipeRepo recipeRepository;

    @Autowired
    private UserRepo userRepository;

    @Autowired
    private CategoryRepo categoryRepository;

    private final String IMAGE_DIR = "src/main/resources/images/";

    public RecipeDto createRecipe(String name, String description, UUID userId, Set<UUID> categoryIds, MultipartFile image) throws IOException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException("User not found for id: " + userId));

        Set<Category> categories = new HashSet<>(categoryRepository.findAllById(categoryIds));
        if (categories.isEmpty()) {
            throw new CustomException("Categories not found for ids: " + categoryIds);
        }

        String imagePath = null;
        if (image != null) {
            try {
                imagePath = saveImage(image);
            } catch (IOException e) {
                throw new CustomException("Failed to store image", e);
            }
        }

        Recipe recipe = new Recipe();
        recipe.setName(name);
        recipe.setDescription(description);
        recipe.setUser(user);
        recipe.setCategories(categories);
        recipe.setImagePath(imagePath);

        Recipe savedRecipe = recipeRepository.save(recipe);

        return mapToDTO(savedRecipe);
    }

    public RecipeDto getRecipe(UUID id) {
        Recipe recipe = recipeRepository.findById(id)
                .orElseThrow(() -> new CustomException("Recipe not found for id: " + id));
        return mapToDTO(recipe);
    }

    public List<RecipeDto> getAllRecipes() {
        List<Recipe> recipes = recipeRepository.findAll();
        if (recipes.isEmpty()) {
            throw new CustomException("No recipes found");
        }
        return recipes.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public RecipeDto updateRecipe(UUID id, String name, String description, Set<UUID> categoryIds, MultipartFile image) throws IOException {
        Recipe recipe = recipeRepository.findById(id)
                .orElseThrow(() -> new CustomException("Recipe not found for id: " + id));

        recipe.setName(name);
        recipe.setDescription(description);

        if (categoryIds != null) {
            Set<Category> categories = new HashSet<>(categoryRepository.findAllById(categoryIds));
            if (categories.isEmpty()) {
                throw new CustomException("Categories not found for ids: " + categoryIds);
            }
            recipe.setCategories(categories);
        }

        if (image != null) {
            try {
                String imagePath = saveImage(image);
                recipe.setImagePath(imagePath);
            } catch (IOException e) {
                throw new CustomException("Failed to store image", e);
            }
        }

        Recipe updatedRecipe = recipeRepository.save(recipe);

        return mapToDTO(updatedRecipe);
    }

    public void deleteRecipe(UUID id) {
        Recipe recipe = recipeRepository.findById(id)
                .orElseThrow(() -> new CustomException("Recipe not found for id: " + id));
        recipeRepository.delete(recipe);
    }

    private String saveImage(MultipartFile image) throws IOException {
        String imagePath = IMAGE_DIR + UUID.randomUUID() + "_" + image.getOriginalFilename();
        File directory = new File(IMAGE_DIR);
        if (!directory.exists()) {
            directory.mkdirs();
        }
        try {
            Files.copy(image.getInputStream(), Paths.get(imagePath));
        } catch (IOException e) {
            throw new CustomException("Error saving image to disk", e);
        }
        return imagePath;
    }

    private RecipeDto mapToDTO(Recipe recipe) {
        RecipeDto dto = new RecipeDto();
        dto.setId(recipe.getId());
        dto.setName(recipe.getName());
        dto.setDescription(recipe.getDescription());
        dto.setImagePath(recipe.getImagePath());
        dto.setCategories(recipe.getCategories().stream().map(Category::getName).collect(Collectors.toSet()));

        // Map the User to UserDTO
        UserDto userDTO = new UserDto();
        userDTO.setId(recipe.getUser().getId().toString());
        userDTO.setUsername(recipe.getUser().getUsername());
        userDTO.setEmail(recipe.getUser().getEmail());

        dto.setUser(userDTO);

        return dto;
    }
}


