package org.cimba.backend.recipe.recipeUser;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.cimba.backend.common.BaseEntity;
import org.cimba.backend.ingredient.Ingredient;
import org.cimba.backend.user.User;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
@Table
public class RecipeUser extends BaseEntity {

    private String name;
    private String description;
    private String imageURL;


    @ElementCollection
    @CollectionTable(
            name = "recipe_library_ingredients",
            joinColumns = @JoinColumn(name = "recipe_id")
    )
    @Column(name = "ingredient", length = 500)
    private List<String> ingredients = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User owner;

}
