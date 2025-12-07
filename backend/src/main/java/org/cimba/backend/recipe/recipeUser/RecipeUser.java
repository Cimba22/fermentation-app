package org.cimba.backend.recipe.recipeUser;

import jakarta.persistence.*;
import lombok.*;
import org.cimba.backend.common.BaseEntity;
import org.cimba.backend.ingredient.Ingredient;
import org.cimba.backend.user.User;

import java.util.List;

@Setter
@Getter
@Entity
@Table
public class RecipeUser extends BaseEntity {

    private String name;
    private String description;
    private String imageURL;


    @ManyToMany
    @JoinTable(
            name = "recipe_user_ingredient",
            joinColumns = @JoinColumn(name = "recipe_id"),
            inverseJoinColumns = @JoinColumn(name = "ingredient_id")
    )
    private List<Ingredient> ingredients;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

}
