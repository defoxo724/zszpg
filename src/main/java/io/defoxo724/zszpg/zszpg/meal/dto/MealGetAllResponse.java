package io.defoxo724.zszpg.zszpg.meal.dto;

import java.util.ArrayList;
import java.util.List;

import io.defoxo724.zszpg.zszpg.meal.MealModel;
import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MealGetAllResponse {
    private List<MealGetAllResponseListElement> meals;

    public static MealGetAllResponse fromModelList(List<MealModel> meals) {
        List<MealGetAllResponseListElement> mealList = new ArrayList<>();
        for (MealModel meal : meals) {
            mealList.add(MealGetAllResponseListElement.fromModel(meal));
        }
        return new MealGetAllResponse(mealList);
    }

}
