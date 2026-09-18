package io.defoxo724.zszpg.zszpg.meal_category.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import io.defoxo724.zszpg.zszpg.meal_category.MealCategoryModel;

class MealCategoryUpdateResponseTest {
    @Test
    void WhenCalled_FromModel_ShouldMapCategoryFields() {
        /* Arrange */
        MealCategoryModel category = new MealCategoryModel(1L, "Lunch");
        /* Act */
        MealCategoryUpdateResponse result = MealCategoryUpdateResponse.fromModel(category);
        /* Assert */
        assertEquals(new MealCategoryUpdateResponse(1L, "Lunch"), result);
    }
}
