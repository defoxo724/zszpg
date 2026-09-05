package io.defoxo724.zszpg.zszpg.meal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import io.defoxo724.zszpg.zszpg.meal.dto.MealCreateRequest;
import io.defoxo724.zszpg.zszpg.meal.dto.MealCreateResponse;
import io.defoxo724.zszpg.zszpg.meal.dto.MealGetAllResponse;
import io.defoxo724.zszpg.zszpg.meal.dto.MealGetByIdResponse;
import io.defoxo724.zszpg.zszpg.meal.dto.MealUpdateRequest;
import io.defoxo724.zszpg.zszpg.meal.dto.MealUpdateResponse;

@ExtendWith(MockitoExtension.class)
public class MealControllerTest {
    @Mock
    private MealService mealService;

    @InjectMocks
    private MealController mealController;

    @Test
    void CreateModel_ShouldSaveAndReturnSavedModel() {
        // Arrange
        MealCreateRequest request = new MealCreateRequest("Pizza", "Margherita", new BigDecimal("32.00"));
        MealModel savedMeal = new MealModel(1L, "Pizza", "Margherita", new BigDecimal("32.00"));

        when(mealService.createMeal(any(MealModel.class))).thenReturn(savedMeal);

        // Act
        ResponseEntity<MealCreateResponse> result = mealController.createMeal(request);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(new MealCreateResponse(1L, "Pizza", "Margherita", new BigDecimal("32.00")), result.getBody());
    }

    @Test
    void WhenCalled_GetMealById_IfMealExists_ShouldReturnMeal() {
        // Arrange
        MealModel meal = new MealModel(1L, "Pizza", "Margherita", new BigDecimal("32.00"));

        when(mealService.getMealById(1L)).thenReturn(meal);

        // Act
        ResponseEntity<MealGetByIdResponse> result = mealController.getMealById(1L);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(new MealGetByIdResponse(1L, "Pizza", "Margherita", new BigDecimal("32.00")), result.getBody());
    }

    @Test
    void WhenCalled_GetMealById_IfMealDoesNotExist_ShouldReturnNotFound() {
        // Arrange
        when(mealService.getMealById(1L)).thenReturn(null);

        // Act
        ResponseEntity<MealGetByIdResponse> result = mealController.getMealById(1L);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
        assertNull(result.getBody());
    }

    @Test
    void WhenCalled_GetAllMeals_ShouldReturnAllMeals() {
        // Arrange
        List<MealModel> meals = List.of(
                new MealModel(1L, "Pizza", "Margherita", new BigDecimal("32.00")),
                new MealModel(2L, "Burger", "Cheeseburger", new BigDecimal("28.00")));

        when(mealService.getAllMeals()).thenReturn(meals);

        // Act
        ResponseEntity<MealGetAllResponse> result = mealController.getAllMeals();

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(MealGetAllResponse.fromModelList(meals), result.getBody());
    }

    @Test
    void WhenCalled_UpdateMealById_IfMealExists_ShouldReturnUpdatedMeal() {
        // Arrange
        Long id = 1L;
        MealUpdateRequest request = new MealUpdateRequest("Pizza", "Capricciosa", new BigDecimal("35.00"));
        MealModel updatedMeal = new MealModel(id, "Pizza", "Capricciosa", new BigDecimal("35.00"));

        when(mealService.updateMealById(eq(id), any(MealModel.class))).thenReturn(updatedMeal);

        // Act
        ResponseEntity<MealUpdateResponse> result = mealController.updateMealById(id, request);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(new MealUpdateResponse(id, "Pizza", "Capricciosa", new BigDecimal("35.00")), result.getBody());
    }

    @Test
    void WhenCalled_UpdateMealById_IfMealDoesNotExist_ShouldReturnNotFound() {
        // Arrange
        Long id = 1L;
        MealUpdateRequest request = new MealUpdateRequest("Pizza", "Capricciosa", new BigDecimal("35.00"));

        when(mealService.updateMealById(eq(id), any(MealModel.class))).thenReturn(null);

        // Act
        ResponseEntity<MealUpdateResponse> result = mealController.updateMealById(id, request);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
        assertNull(result.getBody());
    }

    @Test
    void WhenCalled_DeleteMeal_ShouldDeleteMealAndReturnNoContent() {
        // Arrange
        Long id = 1L;

        // Act
        ResponseEntity<Void> result = mealController.deleteMeal(id);

        // Assert
        verify(mealService).deleteMealById(id);
        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
        assertNull(result.getBody());
    }
}
