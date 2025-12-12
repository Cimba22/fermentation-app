package org.cimba.backend.recipe.recipeUser;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;

public record RecipeUserRequest (
        @NotBlank
        @Size(min = 2, max = 100)
        String name,

        @Size(max = 500)
        String description,

        String imageUrl,

        List<String> ingredients
) {}
