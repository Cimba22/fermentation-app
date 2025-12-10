package org.cimba.backend.recipe.libraryRecipe;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/library/recipes")
@RequiredArgsConstructor
@Validated
public class RecipeLibraryController {
    private final RecipeLibraryService service;
    private final RecipeLibraryMapper mapper;

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

    /**
     * GET /api/library/recipes/{id}
     * Получить один рецепт по id. Доступен всем.
     */
    @GetMapping("/{id}")
    public ResponseEntity<RecipeLibraryResponse> getById(@PathVariable long id) {
        RecipeLibrary library = service.findById(id);
        return ResponseEntity.ok(mapper.toResponse(library));
    }

    /**
     * POST /api/library/recipes
     * Создать базовый рецепт. Доступно только админам.
     * Возвращает 201 Created и Location заголовок на созданный ресурс.
     */
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<RecipeLibraryResponse> create(
            @Valid @RequestBody RecipeLibraryRequest request,
            Authentication authentication
    ){
        RecipeLibrary created = service.create(request, authentication);
        RecipeLibraryResponse response = mapper.toResponse(created);
        URI location = URI.create("/api/library/recipes/" + response.id());
        return ResponseEntity.created(location).body(response);
    }

    /**
     * PUT /api/library/recipes/{id}
     * Обновить базовый рецепт. Только админ.
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<RecipeLibraryResponse> update(
            @PathVariable("id") Long id,
            @Valid
            @RequestBody RecipeLibraryRequest request,
            Authentication authentication
    ){
        RecipeLibrary updated = service.update(id, request, authentication);
        return ResponseEntity.ok(mapper.toResponse(updated));
    }

    /**
     * DELETE /api/library/recipes/{id}
     * Удалить рецепт. Только админ.
     * Возвращает 204 No Content.
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<RecipeLibraryResponse> delete(
            @PathVariable("id") Long id,
            Authentication authentication
    ){
        service.delete(id, authentication);
        return ResponseEntity.noContent().build();
    }

}
