package org.cimba.backend.recipe.recipeUser;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RecipeUserMapper {

    public RecipeUser toEntity(RecipeUserRequest r) {
        return RecipeUser.builder()
                .name(r.name())
                .description(r.description())
                .imageURL(r.imageUrl())
                .ingredients(r.ingredients())
                .build();
    }

    public RecipeUserResponse toResponse(RecipeUser e) {
        return new  RecipeUserResponse(
                e.getId(),
                e.getName(),
                e.getDescription(),
                e.getImageURL(),
                e.getIngredients(),
                e.getCreatedDate(),
                e.getLastModifiedDate()
        );
    }
}
