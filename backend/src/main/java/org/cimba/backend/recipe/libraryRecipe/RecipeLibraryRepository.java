package org.cimba.backend.recipe.libraryRecipe;

import org.cimba.backend.recipe.recipeUser.RecipeUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeLibraryRepository extends JpaRepository<RecipeUser, Long> {
}
