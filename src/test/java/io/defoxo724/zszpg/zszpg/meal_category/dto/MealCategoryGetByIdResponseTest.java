package io.defoxo724.zszpg.zszpg.meal_category.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import io.defoxo724.zszpg.zszpg.meal_category.MealCategoryModel;

class MealCategoryGetByIdResponseTest {
    @Test
    void WhenCalled_FromModel_ShouldMapCategoryFields() {
        /* Arrange */
        MealCategoryModel category = new MealCategoryModel(1L, "Dinner");
        /* Act */
        MealCategoryGetByIdResponse result = MealCategoryGetByIdResponse.fromModel(category);
        /* Assert */
        assertEquals(new MealCategoryGetByIdResponse(1L, "Dinner"), result);
    }
}
