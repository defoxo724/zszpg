package io.defoxo724.zszpg.zszpg.meal_category.dto;

import java.util.ArrayList;
import java.util.List;

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
public class MealCategoryGetAllResponse {
    private List<MealCategoryGetAllResponseListElement> mealCategories;

    public static MealCategoryGetAllResponse fromModelList(List<MealCategoryModel> mealCategories) {
        List<MealCategoryGetAllResponseListElement> mealCategoryList = new ArrayList<>();
        for (MealCategoryModel mealCategory : mealCategories) {
            mealCategoryList.add(MealCategoryGetAllResponseListElement.fromModel(mealCategory));
        }
        return new MealCategoryGetAllResponse(mealCategoryList);
    }

}
