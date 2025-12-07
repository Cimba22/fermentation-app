package org.cimba.backend.recipe.libraryRecipe;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RecipeLibraryRepository extends JpaRepository<RecipeLibrary, Long> {
    @Query("""
        SELECT r FROM RecipeLibrary r
        WHERE LOWER(r.name) LIKE %:text%
           OR LOWER(r.description) LIKE %:text%
    """)
    Page<RecipeLibrary> search(@Param("text") String text, Pageable pageable);
}

