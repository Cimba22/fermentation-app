package org.cimba.backend.recipe.recipeUser;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping(
        value = "/user/recipes",
        produces = "application/json"
)
@RequiredArgsConstructor
@Validated
public class RecipeUserController {
    private final RecipeUserService service;
    private final RecipeUserMapper mapper;

    /**
     * GET /user/recipes
     * Returns a paginated list of recipes belonging to the authenticated user.
     * Supports optional text search via `q`.
     */
    @GetMapping
    public ResponseEntity<Page<RecipeUserResponse>> getAllRecipes(
            @RequestParam(value = "q", required = false) String query,
            Authentication authentication,
            Pageable pageable
    ) {
        Page<RecipeUser> page = service.getMyRecipes(query, pageable, authentication);
        return ResponseEntity.ok(page.map(mapper::toResponse));
    }

    /**
     * GET /user/recipes/{id}
     * Returns a single recipe by ID if it belongs to the authenticated user.
     */
    @GetMapping("/{id}")
    public ResponseEntity<RecipeUserResponse> getById(
            @PathVariable long id,
            Authentication authentication
    ) {
        RecipeUser recipe = service.findByIdForOwner(id, authentication);
        return ResponseEntity.ok(mapper.toResponse(recipe));
    }

    /**
     * POST /user/recipes
     * Creates a new recipe for the authenticated user.
     * Returns 201 Created with the Location header.
     */
    @PostMapping(consumes = "application/json")
    public ResponseEntity<RecipeUserResponse> create(
            @Valid @RequestBody RecipeUserRequest request,
            Authentication authentication
    ) {
        RecipeUser created = service.create(request, authentication);
        RecipeUserResponse response = mapper.toResponse(created);

        URI location = URI.create("/api/user/recipes/" + response.id());
        return ResponseEntity.created(location).body(response);
    }

    /**
     * PUT /user/recipes/{id}
     * Updates an existing recipe owned by the authenticated user.
     */
    @PutMapping(value = "/{id}", consumes = "application/json")
    public ResponseEntity<RecipeUserResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody RecipeUserRequest request,
            Authentication authentication
    ) {
        RecipeUser updated = service.update(id, request, authentication);
        return ResponseEntity.ok(mapper.toResponse(updated));
    }

    /**
     * DELETE /user/recipes/{id}
     * Deletes a recipe owned by the authenticated user.
     * Returns 204 No Content.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id,
            Authentication authentication
    ) {
        service.delete(id, authentication);
        return ResponseEntity.noContent().build();
    }
}
