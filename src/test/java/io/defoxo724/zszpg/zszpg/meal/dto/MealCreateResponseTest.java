package io.defoxo724.zszpg.zszpg.meal.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import io.defoxo724.zszpg.zszpg.meal.MealModel;
import io.defoxo724.zszpg.zszpg.meal_category.MealCategoryModel;

class MealCreateResponseTest {
    @Test
    void WhenCalled_FromModel_ShouldMapMealFields() {
        /* Arrange */
        MealModel meal = new MealModel(1L, "Pizza", "Margherita", new BigDecimal("32.00"),
                new MealCategoryModel(5L, "Main course"));
        /* Act */
        MealCreateResponse result = MealCreateResponse.fromModel(meal);
        /* Assert */
        assertEquals(new MealCreateResponse(1L, "Pizza", "Margherita", new BigDecimal("32.00"), 5L), result);
    }
}
