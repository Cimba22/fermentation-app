package org.cimba.backend.recipe.libraryRecipe;




import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record RecipeLibraryResponse(
        Long id,
        String name,
        String description,
        String imageUrl,
        List<Long> ingredientIds,
        LocalDateTime createdDate,
        LocalDateTime lastModifiedDate
) {}
