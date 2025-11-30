package org.cimba.backend.recipe.libraryRecipe;


import jakarta.persistence.*;
import lombok.*;
import org.cimba.backend.common.BaseEntity;
import org.cimba.backend.ingredient.Ingredient;

import java.util.List;


@Entity
@Table(name = "recipe_library")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecipeLibrary extends BaseEntity {

    private String name;
    private String description;
    private String imageUrl;

    @ManyToMany
    @JoinTable(
            name = "recipe_library_ingredient",
            joinColumns = @JoinColumn(name = "recipe_id"),
            inverseJoinColumns = @JoinColumn(name = "ingredient_id")
    )
    private List<Ingredient> ingredients;


}
