package org.cimba.backend.recipe.libraryRecipe;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/library/recipes")
@RequiredArgsConstructor
@Validated
public class RecipeLibraryController {
    private RecipeLibraryService service;
    private RecipeLibraryMapper mapper;

    /**
     * GET /api/library/recipes
     * Пагинированный список всех базовых рецептов. Доступен всем.
     * Можно добавлять параметры поиска/фильтрации по мере надобности (пример - q).
     */
    @GetMapping
    public ResponseEntity<Page<RecipeLibraryResponse>> getAllRecipes(
            @RequestParam(value = "q", required = false) String q,
            Pageable pageable
    ) {
        Page<RecipeLibrary> page = service.getAllRecipes(q, pageable);
        Page<RecipeLibraryResponse> response = page.map(mapper::toResponse);
        return ResponseEntity.ok(response);
    }


}
