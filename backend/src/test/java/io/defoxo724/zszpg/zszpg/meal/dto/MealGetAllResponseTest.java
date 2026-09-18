package io.defoxo724.zszpg.zszpg.meal.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.Test;
import io.defoxo724.zszpg.zszpg.meal.MealModel;
import io.defoxo724.zszpg.zszpg.meal_category.MealCategoryModel;

class MealGetAllResponseTest {
    @Test
    void WhenCalled_FromModelList_ShouldMapEveryMeal() {
        /* Arrange */
        MealCategoryModel category = new MealCategoryModel(5L, "Main course");
        List<MealModel> meals = List.of(
                new MealModel(1L, "Pizza", "Margherita", new BigDecimal("32.00"), category),
                new MealModel(2L, "Burger", "Cheeseburger", new BigDecimal("28.00"), category));
        /* Act */
        MealGetAllResponse result = MealGetAllResponse.fromModelList(meals);
        /* Assert */
        assertEquals(List.of(
                new MealGetAllResponseListElement(1L, "Pizza", "Margherita", new BigDecimal("32.00"), 5L),
                new MealGetAllResponseListElement(2L, "Burger", "Cheeseburger", new BigDecimal("28.00"), 5L)),
                result.getMeals());
    }
}
