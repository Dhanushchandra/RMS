package com.recipe.RMS.repository;

import com.recipe.RMS.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RecipeRepo extends JpaRepository<Recipe, UUID> {
}
