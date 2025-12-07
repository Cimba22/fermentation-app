package org.cimba.backend.recipe.libraryRecipe;

import org.cimba.backend.recipe.recipeUser.RecipeUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeLibraryRepository extends JpaRepository<RecipeLibrary, Long> {
    Page<RecipeLibrary> search(String lowerCase, Pageable pageable);
}
