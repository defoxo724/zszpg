package io.defoxo724.zszpg.zszpg.meal_category.dto;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import io.defoxo724.zszpg.zszpg.meal_category.MealCategoryModel;

class MealCategoryCreateRequestTest {
    @Test
    void WhenCalled_ToModel_ShouldMapRequestFields() {
        /* Arrange */
        MealCategoryCreateRequest request = new MealCategoryCreateRequest("Dinner");
        /* Act */
        MealCategoryModel result = request.toModel();
        /* Assert */
        assertNull(result.getId());
        assertEquals("Dinner", result.getName());
        assertNull(result.getMeals());
    }
}
