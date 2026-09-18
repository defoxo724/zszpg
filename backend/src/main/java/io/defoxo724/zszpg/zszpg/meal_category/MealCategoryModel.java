package io.defoxo724.zszpg.zszpg.meal_category;

import java.util.List;

import io.defoxo724.zszpg.zszpg.meal.MealModel;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MealCategoryModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @OneToMany(mappedBy = "mealCategory")
    private List<MealModel> meals;

    public MealCategoryModel(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
