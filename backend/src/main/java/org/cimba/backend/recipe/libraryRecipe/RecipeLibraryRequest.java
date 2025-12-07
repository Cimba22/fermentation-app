package org.cimba.backend.recipe.libraryRecipe;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;

public record RecipeLibraryRequest(

        @NotBlank(message = "name is required")
        @Size(max = 200)
        String name,

        @Size(max = 2000)
        String description,

        String imageUrl,

        // список id ингредиентов из справочника
        List<Long> ingredientIds
) {}
