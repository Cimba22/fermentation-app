package org.cimba.backend.recipe.libraryRecipe;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;

public interface RecipeLibraryService {

    Page<RecipeLibrary> getAllRecipes(String q, Pageable pageable);

    RecipeLibrary findById(long id);

    RecipeLibrary create(@Valid RecipeLibraryRequest request, Authentication auth);

    RecipeLibrary update(Long id, @Valid RecipeLibraryRequest request, Authentication auth);

    void delete(Long id, Authentication auth);
}

