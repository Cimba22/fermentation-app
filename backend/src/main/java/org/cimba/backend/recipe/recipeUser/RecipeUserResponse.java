package org.cimba.backend.recipe.recipeUser;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record RecipeUserResponse (
    Long id,
    String name,
    String description,
    String imageUrl,
    List<String> ingredients,
    LocalDateTime createdDate,
    LocalDateTime lastModifiedDate
){}
