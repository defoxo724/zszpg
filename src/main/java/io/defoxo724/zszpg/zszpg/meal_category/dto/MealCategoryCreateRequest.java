package io.defoxo724.zszpg.zszpg.meal_category.dto;

import io.defoxo724.zszpg.zszpg.meal_category.MealCategoryModel;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class MealCategoryCreateRequest {
    private String name;

    public MealCategoryModel toModel() {
        return new MealCategoryModel(null, name, null);
    }
}
