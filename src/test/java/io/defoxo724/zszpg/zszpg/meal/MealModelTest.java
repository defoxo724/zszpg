package io.defoxo724.zszpg.zszpg.meal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

import io.defoxo724.zszpg.zszpg.meal_category.MealCategoryModel;

class MealModelTest {
    @Test
    void WhenCalled_AllArgsConstructor_ShouldSetAllFields() {
        /* Arrange */
        MealCategoryModel category = new MealCategoryModel(5L, "Main course");

        /* Act */
        MealModel meal = new MealModel(1L, "Pizza", "Margherita", new BigDecimal("32.00"), category);

        /* Assert */
        assertEquals(1L, meal.getId());
        assertEquals("Pizza", meal.getName());
        assertEquals("Margherita", meal.getDescription());
        assertEquals(new BigDecimal("32.00"), meal.getPrice());
        assertSame(category, meal.getMealCategory());
    }
}
