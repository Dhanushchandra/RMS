package com.recipe.RMS.repository;

import com.recipe.RMS.model.Category;
import com.recipe.RMS.model.Recipe;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RecipeRepo extends JpaRepository<Recipe, UUID> {

    Page<Recipe> findDistinctByCategoriesIn(List<Category> categories, Pageable pageable);

    Page<Recipe> findByNameContainingIgnoreCase(String name, Pageable pageable);

}
