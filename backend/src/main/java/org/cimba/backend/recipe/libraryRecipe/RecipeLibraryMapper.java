package org.cimba.backend.recipe.libraryRecipe;

import lombok.RequiredArgsConstructor;
import org.cimba.backend.ingredient.Ingredient;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;


/**RecipeLibraryMapper — обычный маппер, который:
 превращает DTO → ENTITY (toEntity)
 превращает ENTITY → DTO (toResponse)
 помогает сервису собирать ингредиенты по списку id
 Он не делает бизнес-логику и не ходит в базу напрямую (кроме resolveIngredients, но это скорее хелпер).
 **/
@Component
@RequiredArgsConstructor
public class RecipeLibraryMapper {


    public RecipeLibrary toEntity(RecipeLibraryRequest req) {
        return RecipeLibrary.builder()
                .name(req.name())
                .description(req.description())
                .imageUrl(req.imageUrl())
                .ingredients(req.ingredients() != null ? req.ingredients() : List.of())
                .build();
    }

    public RecipeLibraryResponse toResponse(RecipeLibrary entity) {
        return new RecipeLibraryResponse(
                entity.getId(),
                entity.getName(),
                entity.getDescription(),
                entity.getImageUrl(),
                entity.getIngredients(),
                entity.getCreatedDate(),
                entity.getLastModifiedDate()
        );
    }

    public List<Ingredient> resolveIngredients(List<Long> ids) {
        if (ids == null || ids.isEmpty()) return Collections.emptyList();
        return ingredientRepository.findAllById(ids);
    }
}

