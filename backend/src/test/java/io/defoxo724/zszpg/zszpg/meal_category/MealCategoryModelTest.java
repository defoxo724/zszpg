package io.defoxo724.zszpg.zszpg.meal_category;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

class MealCategoryModelTest {
    @Test
    void WhenCalled_IdAndNameConstructor_ShouldSetProvidedFields() {
        /* Arrange */
        Long id = 1L;
        String name = "Dinner";

        /* Act */
        MealCategoryModel category = new MealCategoryModel(id, name);

        /* Assert */
        assertEquals(id, category.getId());
        assertEquals(name, category.getName());
        assertNull(category.getMeals());
    }
}
