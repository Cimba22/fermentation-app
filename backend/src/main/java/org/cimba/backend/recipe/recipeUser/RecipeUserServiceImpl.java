package org.cimba.backend.recipe.recipeUser;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.cimba.backend.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RecipeUserServiceImpl  implements RecipeUserService {
    private final RecipeUserRepository repository;
    private final RecipeUserMapper mapper;

    @Override
    public Page<RecipeUser> getMyRecipes(String q, Pageable pageable, Authentication authentication) {
        User user = (User) authentication.getPrincipal();

        if(q == null || q.isBlank()) {
            return repository.findByOwnerId(user.getId(), pageable);
        }
        return repository.search(user.getId(), q.toLowerCase(), pageable);
    }

    @Override
    public RecipeUser findByIdForOwner(long id,  Authentication authentication) {
        User user = (User) authentication.getPrincipal();

        RecipeUser recipe = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Recipe not found with ID " + id));

        if (!recipe.getOwner().getId().equals(user.getId())) {
            throw new AccessDeniedException("This recipe belongs to another user");
        }

        return recipe;
    }

    @Override
    public RecipeUser create(RecipeUserRequest request, Authentication authentication) {
        User user = (User) authentication.getPrincipal();

        RecipeUser recipe = mapper.toEntity(request);
        recipe.setOwner(user);

        return repository.save(recipe);
    }

    @Override
    public RecipeUser update(Long id, RecipeUserRequest request, Authentication authentication) {

        RecipeUser existing = findByIdForOwner(id, authentication);

        existing.setName(request.name());
        existing.setDescription(request.description());
        existing.setImageURL(request.imageUrl());
        existing.setIngredients(request.ingredients());

        return repository.save(existing);
    }

    @Override
    public void delete(Long id, Authentication authentication) {
        RecipeUser recipe = findByIdForOwner(id, authentication);
        repository.delete(recipe);
    }
}
