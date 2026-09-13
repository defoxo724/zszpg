package io.defoxo724.zszpg.zszpg.meal_category.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import io.defoxo724.zszpg.zszpg.meal_category.MealCategoryModel;

class MealCategoryGetAllResponseListElementTest {
    @Test
    void WhenCalled_FromModel_ShouldMapCategoryFields() {
        /* Arrange */
        MealCategoryModel category = new MealCategoryModel(1L, "Dinner");
        /* Act */
        MealCategoryGetAllResponseListElement result = MealCategoryGetAllResponseListElement.fromModel(category);
        /* Assert */
        assertEquals(new MealCategoryGetAllResponseListElement(1L, "Dinner"), result);
    }
}
