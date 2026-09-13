package io.defoxo724.zszpg.zszpg.meal_category.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import io.defoxo724.zszpg.zszpg.meal_category.MealCategoryModel;

class MealCategoryCreateResponseTest {
    @Test
    void WhenCalled_FromModel_ShouldMapCategoryFields() {
        /* Arrange */
        MealCategoryModel category = new MealCategoryModel(1L, "Dinner");
        /* Act */
        MealCategoryCreateResponse result = MealCategoryCreateResponse.fromModel(category);
        /* Assert */
        assertEquals(new MealCategoryCreateResponse(1L, "Dinner"), result);
    }
}
