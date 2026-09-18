package io.defoxo724.zszpg.zszpg.meal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
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
import io.defoxo724.zszpg.zszpg.meal_category.MealCategoryModel;

@ExtendWith(MockitoExtension.class)
class MealControllerTest {
    @Mock
    private MealService mealService;
    @InjectMocks
    private MealController mealController;

    @Test
    void WhenCalled_CreateMeal_WithExistingCategory_ShouldReturnCreatedMeal() {
        /* Arrange */
        MealCreateRequest request = new MealCreateRequest("Pizza", "Margherita", new BigDecimal("32.00"), 5L);
        MealModel saved = meal(1L, "Pizza", "Margherita", "32.00");
        when(mealService.createMeal(any(MealModel.class), org.mockito.ArgumentMatchers.eq(5L))).thenReturn(saved);

        /* Act */
        ResponseEntity<MealCreateResponse> result = mealController.createMeal(request);

        /* Assert */
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(new MealCreateResponse(1L, "Pizza", "Margherita", new BigDecimal("32.00"), 5L), result.getBody());
    }

    @Test
    void WhenCalled_CreateMeal_WithMissingCategory_ShouldReturnNotFound() {
        /* Arrange */
        MealCreateRequest request = new MealCreateRequest("Pizza", "Margherita", new BigDecimal("32.00"), 5L);
        when(mealService.createMeal(any(MealModel.class), org.mockito.ArgumentMatchers.eq(5L))).thenReturn(null);

        /* Act */
        ResponseEntity<MealCreateResponse> result = mealController.createMeal(request);

        /* Assert */
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
        assertNull(result.getBody());
    }

    @Test
    void WhenCalled_GetMealById_WithExistingMeal_ShouldReturnMeal() {
        /* Arrange */
        when(mealService.getMealById(1L)).thenReturn(meal(1L, "Pizza", "Margherita", "32.00"));

        /* Act */
        ResponseEntity<MealGetByIdResponse> result = mealController.getMealById(1L);

        /* Assert */
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(new MealGetByIdResponse(1L, "Pizza", "Margherita", new BigDecimal("32.00"), 5L), result.getBody());
    }

    @Test
    void WhenCalled_GetMealById_WithMissingMeal_ShouldReturnNotFound() {
        /* Arrange */
        when(mealService.getMealById(1L)).thenReturn(null);

        /* Act */
        ResponseEntity<MealGetByIdResponse> result = mealController.getMealById(1L);

        /* Assert */
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
        assertNull(result.getBody());
    }

    @Test
    void WhenCalled_GetAllMeals_ShouldReturnAllMeals() {
        /* Arrange */
        List<MealModel> meals = List.of(meal(1L, "Pizza", "Margherita", "32.00"));
        when(mealService.getAllMeals()).thenReturn(meals);

        /* Act */
        ResponseEntity<MealGetAllResponse> result = mealController.getAllMeals();

        /* Assert */
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(MealGetAllResponse.fromModelList(meals), result.getBody());
    }

    @Test
    void WhenCalled_UpdateMealById_WithExistingMealAndCategory_ShouldReturnUpdatedMeal() {
        /* Arrange */
        MealUpdateRequest request = new MealUpdateRequest("Pizza", "Capricciosa", new BigDecimal("35.00"), 5L);
        MealModel updated = meal(1L, "Pizza", "Capricciosa", "35.00");
        when(mealService.updateMealById(org.mockito.ArgumentMatchers.eq(1L), any(MealModel.class),
                org.mockito.ArgumentMatchers.eq(5L))).thenReturn(updated);

        /* Act */
        ResponseEntity<MealUpdateResponse> result = mealController.updateMealById(1L, request);

        /* Assert */
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(new MealUpdateResponse(1L, "Pizza", "Capricciosa", new BigDecimal("35.00"), 5L), result.getBody());
    }

    @Test
    void WhenCalled_UpdateMealById_WithMissingMealOrCategory_ShouldReturnNotFound() {
        /* Arrange */
        MealUpdateRequest request = new MealUpdateRequest("Pizza", "Capricciosa", new BigDecimal("35.00"), 5L);
        when(mealService.updateMealById(org.mockito.ArgumentMatchers.eq(1L), any(MealModel.class),
                org.mockito.ArgumentMatchers.eq(5L))).thenReturn(null);

        /* Act */
        ResponseEntity<MealUpdateResponse> result = mealController.updateMealById(1L, request);

        /* Assert */
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
        assertNull(result.getBody());
    }

    @Test
    void WhenCalled_DeleteMeal_ShouldReturnNoContent() {
        /* Arrange */
        Long id = 1L;

        /* Act */
        ResponseEntity<Void> result = mealController.deleteMeal(id);

        /* Assert */
        verify(mealService).deleteMealById(id);
        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
        assertNull(result.getBody());
    }

    private MealModel meal(Long id, String name, String description, String price) {
        return new MealModel(id, name, description, new BigDecimal(price), new MealCategoryModel(5L, "Main course"));
    }
}
