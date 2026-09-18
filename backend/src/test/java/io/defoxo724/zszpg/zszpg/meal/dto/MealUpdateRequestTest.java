package io.defoxo724.zszpg.zszpg.meal.dto;

import static org.junit.jupiter.api.Assertions.*;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import io.defoxo724.zszpg.zszpg.meal.MealModel;

class MealUpdateRequestTest {
    @Test
    void WhenCalled_ToModel_ShouldMapRequestFields() {
        /* Arrange */
        MealUpdateRequest request = new MealUpdateRequest("Pizza", "Capricciosa", new BigDecimal("35.00"), 5L);
        /* Act */
        MealModel result = request.toModel();
        /* Assert */
        assertNull(result.getId());
        assertEquals("Pizza", result.getName());
        assertEquals("Capricciosa", result.getDescription());
        assertEquals(new BigDecimal("35.00"), result.getPrice());
        assertNull(result.getMealCategory());
    }
}
