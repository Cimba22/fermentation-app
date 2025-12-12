package org.cimba.backend.recipe.recipeUser;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;

public interface RecipeUserService {
    Page<RecipeUser> getMyRecipes(String q, Pageable pageable, Authentication authentication);

    RecipeUser findByIdForOwner(long id, Authentication authentication);

    RecipeUser create(@Valid RecipeUserRequest request, Authentication authentication);

    RecipeUser update(Long id, @Valid RecipeUserRequest request, Authentication authentication);

    void delete(Long id, Authentication authentication);
}
