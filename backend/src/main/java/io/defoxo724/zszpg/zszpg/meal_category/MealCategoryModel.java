package io.defoxo724.zszpg.zszpg.meal_category;

import java.util.List;

import io.defoxo724.zszpg.zszpg.meal.MealModel;
import jakarta.persistence.Column;
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
    @Column(name = "image_url", length = 2048)
    private String imageUrl;
    @OneToMany(mappedBy = "mealCategory")
    private List<MealModel> meals;

    public MealCategoryModel(Long id, String name) {
        this(id, name, null, null);
    }

    public MealCategoryModel(Long id, String name, List<MealModel> meals) {
        this(id, name, null, meals);
    }
}
