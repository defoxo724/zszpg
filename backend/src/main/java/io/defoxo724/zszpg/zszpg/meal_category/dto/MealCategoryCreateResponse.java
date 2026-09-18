package io.defoxo724.zszpg.zszpg.meal_category.dto;

import io.defoxo724.zszpg.zszpg.meal_category.MealCategoryModel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class MealCategoryCreateResponse {
    private Long id;
    private String name;

    public static MealCategoryCreateResponse fromModel(MealCategoryModel mealCategory) {
        return new MealCategoryCreateResponse(mealCategory.getId(), mealCategory.getName());
    }

}
