package io.defoxo724.zszpg.zszpg.meal_category.dto;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import io.defoxo724.zszpg.zszpg.meal_category.MealCategoryModel;

class MealCategoryUpdateRequestTest {
    @Test
    void WhenCalled_ToModel_ShouldMapRequestFields() {
        /* Arrange */
        MealCategoryUpdateRequest request = new MealCategoryUpdateRequest("Lunch");
        /* Act */
        MealCategoryModel result = request.toModel();
        /* Assert */
        assertNull(result.getId());
        assertEquals("Lunch", result.getName());
        assertNull(result.getMeals());
    }
}
