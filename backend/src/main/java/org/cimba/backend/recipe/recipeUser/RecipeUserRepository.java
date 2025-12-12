package org.cimba.backend.recipe.recipeUser;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface RecipeUserRepository extends JpaRepository<RecipeUser,Long> {
    @Query("""
        SELECT r FROM RecipeUser r
        WHERE r.owner.id = :ownerId
          AND (LOWER(r.name) LIKE %:text% OR LOWER(r.description) LIKE %:text%)
    """)
    Page<RecipeUser> search(Long ownerId, String text, Pageable pageable);

    Page<RecipeUser> findByOwnerId(Long ownerId, Pageable pageable);

}
