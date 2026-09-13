package io.defoxo724.zszpg.zszpg.meal_category.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.List;
import org.junit.jupiter.api.Test;
import io.defoxo724.zszpg.zszpg.meal_category.MealCategoryModel;

class MealCategoryGetAllResponseTest {
    @Test
    void WhenCalled_FromModelList_ShouldMapEveryCategory() {
        /* Arrange */
        List<MealCategoryModel> categories = List.of(new MealCategoryModel(1L, "Dinner"),
                new MealCategoryModel(2L, "Dessert"));
        /* Act */
        MealCategoryGetAllResponse result = MealCategoryGetAllResponse.fromModelList(categories);
        /* Assert */
        assertEquals(List.of(new MealCategoryGetAllResponseListElement(1L, "Dinner"),
                new MealCategoryGetAllResponseListElement(2L, "Dessert")), result.getMealCategories());
    }
}
