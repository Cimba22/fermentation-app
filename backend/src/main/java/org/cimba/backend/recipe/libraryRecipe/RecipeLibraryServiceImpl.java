package org.cimba.backend.recipe.libraryRecipe;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.cimba.backend.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;




@Service
@RequiredArgsConstructor
public class RecipeLibraryServiceImpl implements RecipeLibraryService {

    private final RecipeLibraryRepository libraryRepository;
    private final RecipeLibraryMapper mapper;

    public Page<RecipeLibrary> getAllRecipes(String q, Pageable pageable) {
        if (q == null || q.isBlank()) {
            return libraryRepository.findAll(pageable);
        }
        return libraryRepository.search(q.toLowerCase(), pageable);
    }

    public RecipeLibrary findById(long id) {
        return libraryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No recipe found with the ID: \\\" + id"));

    }

    public RecipeLibrary create(@Valid RecipeLibraryRequest request, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        RecipeLibrary entity = mapper.toEntity(request);

        entity.setCreatedBy(user.getId());

        if (request.ingredientIds() != null) {
            entity.setIngredients(mapper.resolveIngredients(request.ingredientIds()));
        }

        return libraryRepository.save(entity);
    }

    public RecipeLibrary update(Long id, @Valid RecipeLibraryRequest request, Authentication authentication) {
        User user = (User) authentication.getPrincipal();

        RecipeLibrary existing = findById(id);

        //TODO change it
        if (!user.getRoles().equals("ADMIN")) {
            throw new AccessDeniedException("Access denied");
        }

        existing.setName(request.name());
        existing.setDescription(request.description());
        existing.setImageUrl(request.imageUrl());

        if (request.ingredientIds() != null) {
            existing.setIngredients(mapper.resolveIngredients(request.ingredientIds()));
        }

        return libraryRepository.save(existing);
    }

    public void delete(Long id, Authentication authentication) {
        User user = (User) authentication.getPrincipal();

        //TODO change it admin
        if (!user.getRoles().equals("ADMIN")) {
            throw new AccessDeniedException("Access denied");
        }

        RecipeLibrary recipeLibrary = libraryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No recipe found with the ID: \\" + id));

        libraryRepository.delete(recipeLibrary);
    }
}
