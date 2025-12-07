package org.cimba.backend.recipe.libraryRecipe;


import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.cimba.backend.common.BaseEntity;
import org.cimba.backend.ingredient.Ingredient;

import java.util.List;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "recipe_library")
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
