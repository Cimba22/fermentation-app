package org.cimba.backend.recipe.libraryRecipe;

import lombok.RequiredArgsConstructor;
import org.cimba.backend.ingredient.Ingredient;
import org.cimba.backend.ingredient.IngredientRepository;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


/**RecipeLibraryMapper — обычный маппер, который:
 превращает DTO → ENTITY (toEntity)
 превращает ENTITY → DTO (toResponse)
 помогает сервису собирать ингредиенты по списку id
 Он не делает бизнес-логику и не ходит в базу напрямую (кроме resolveIngredients, но это скорее хелпер).
 **/
@Component
@RequiredArgsConstructor
public class RecipeLibraryMapper {

    private final IngredientRepository ingredientRepository;

    public RecipeLibrary toEntity(RecipeLibraryRequest req) {
        return RecipeLibrary.builder()
                .name(req.name())
                .description(req.description())
                .imageUrl(req.imageUrl())
                // ингредиенты устанавливаются сервисом, иначе маппер слишком умный станет
                .build();
    }

    public RecipeLibraryResponse toResponse(RecipeLibrary entity) {
        List<Long> ingredientIds = entity.getIngredients() == null
                ? Collections.emptyList()
                : entity.getIngredients().stream()
                .map(Ingredient::getId)
                .toList();

        return RecipeLibraryResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .imageUrl(entity.getImageUrl())
                .ingredientIds(ingredientIds)
                .createdDate(entity.getCreatedDate())
                .lastModifiedDate(entity.getLastModifiedDate())
                .build();
    }

    public List<Ingredient> resolveIngredients(List<Long> ids) {
        if (ids == null || ids.isEmpty()) return Collections.emptyList();
        return ingredientRepository.findAllById(ids);
    }
}

