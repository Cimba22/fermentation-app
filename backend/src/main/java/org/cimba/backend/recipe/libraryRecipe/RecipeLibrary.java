package org.cimba.backend.recipe.libraryRecipe;


import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.cimba.backend.common.BaseEntity;
import org.cimba.backend.ingredient.Ingredient;

import java.util.ArrayList;
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

    @ElementCollection
    @CollectionTable(
            name = "recipe_library_ingredients",
            joinColumns = @JoinColumn(name = "recipe_id")
    )
    @Column(name = "ingredient", length = 500)
    private List<String> ingredients = new ArrayList<>();
}
