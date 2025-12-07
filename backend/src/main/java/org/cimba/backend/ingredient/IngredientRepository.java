package org.cimba.backend.ingredient;

import org.cimba.backend.recipe.recipeUser.RecipeUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
}
