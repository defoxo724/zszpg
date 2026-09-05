package io.defoxo724.zszpg.zszpg.meal_category.dto;

import io.defoxo724.zszpg.zszpg.meal_category.MealCategoryModel;
import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class MealCategoryGetAllResponseListElement {
    private Long id;
    private String name;

    public static MealCategoryGetAllResponseListElement fromModel(MealCategoryModel mealCategory) {
        return new MealCategoryGetAllResponseListElement(mealCategory.getId(), mealCategory.getName());
    }

}
