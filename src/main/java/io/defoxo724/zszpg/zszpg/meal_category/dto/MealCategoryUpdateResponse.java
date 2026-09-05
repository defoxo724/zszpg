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
public class MealCategoryUpdateResponse {
    private Long id;
    private String name;

    public static MealCategoryUpdateResponse fromModel(MealCategoryModel mealCategory) {
        return new MealCategoryUpdateResponse(mealCategory.getId(), mealCategory.getName());
    }
}
