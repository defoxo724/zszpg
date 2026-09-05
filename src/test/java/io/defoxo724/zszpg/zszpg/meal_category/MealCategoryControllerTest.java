package io.defoxo724.zszpg.zszpg.meal_category;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import io.defoxo724.zszpg.zszpg.meal_category.dto.MealCategoryCreateRequest;
import io.defoxo724.zszpg.zszpg.meal_category.dto.MealCategoryCreateResponse;
import io.defoxo724.zszpg.zszpg.meal_category.dto.MealCategoryGetAllResponse;
import io.defoxo724.zszpg.zszpg.meal_category.dto.MealCategoryGetByIdResponse;
import io.defoxo724.zszpg.zszpg.meal_category.dto.MealCategoryUpdateRequest;
import io.defoxo724.zszpg.zszpg.meal_category.dto.MealCategoryUpdateResponse;

@ExtendWith(MockitoExtension.class)
public class MealCategoryControllerTest {
    @Mock
    private MealCategoryService mealCategoryService;

    @InjectMocks
    private MealCategoryController mealCategoryController;

    @Test
    void CreateModel_ShouldSaveAndReturnSavedModel() {
        // Arrange
        MealCategoryCreateRequest request = new MealCategoryCreateRequest("Pizza");
        MealCategoryModel savedMealCategory = new MealCategoryModel(1L, "Pizza");

        when(mealCategoryService.createMealCategory(any(MealCategoryModel.class))).thenReturn(savedMealCategory);

        // Act
        ResponseEntity<MealCategoryCreateResponse> result = mealCategoryController.createMealCategory(request);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(new MealCategoryCreateResponse(1L, "Pizza"), result.getBody());
    }

    @Test
    void WhenCalled_GetMealCategoryById_IfMealCategoryExists_ShouldReturnMealCategory() {
        // Arrange
        MealCategoryModel mealCategory = new MealCategoryModel(1L, "Pizza");

        when(mealCategoryService.getMealCategoryById(1L)).thenReturn(mealCategory);

        // Act
        ResponseEntity<MealCategoryGetByIdResponse> result = mealCategoryController.getMealCategoryById(1L);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(new MealCategoryGetByIdResponse(1L, "Pizza"), result.getBody());
    }

    @Test
    void WhenCalled_GetMealCategoryById_IfMealCategoryDoesNotExist_ShouldReturnNotFound() {
        // Arrange
        when(mealCategoryService.getMealCategoryById(1L)).thenReturn(null);

        // Act
        ResponseEntity<MealCategoryGetByIdResponse> result = mealCategoryController.getMealCategoryById(1L);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
        assertNull(result.getBody());
    }

    @Test
    void WhenCalled_GetAllMealCategories_ShouldReturnAllMealCategories() {
        // Arrange
        List<MealCategoryModel> mealCategories = List.of(
                new MealCategoryModel(1L, "Pizza"),
                new MealCategoryModel(2L, "Burger"));

        when(mealCategoryService.getAllMealCategories()).thenReturn(mealCategories);

        // Act
        ResponseEntity<MealCategoryGetAllResponse> result = mealCategoryController.getAllMealCategories();

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(MealCategoryGetAllResponse.fromModelList(mealCategories), result.getBody());
    }

    @Test
    void WhenCalled_UpdateMealCategoryById_IfMealCategoryExists_ShouldReturnUpdatedMealCategory() {
        // Arrange
        Long id = 1L;
        MealCategoryUpdateRequest request = new MealCategoryUpdateRequest("Dinner");
        MealCategoryModel updatedMealCategory = new MealCategoryModel(id, "Dinner");

        when(mealCategoryService.updateMealCategoryById(eq(id), any(MealCategoryModel.class)))
                .thenReturn(updatedMealCategory);

        // Act
        ResponseEntity<MealCategoryUpdateResponse> result = mealCategoryController.updateMealCategoryById(id,
                request);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(new MealCategoryUpdateResponse(id, "Dinner"), result.getBody());
    }

    @Test
    void WhenCalled_UpdateMealCategoryById_IfMealCategoryDoesNotExist_ShouldReturnNotFound() {
        // Arrange
        Long id = 1L;
        MealCategoryUpdateRequest request = new MealCategoryUpdateRequest("Dinner");

        when(mealCategoryService.updateMealCategoryById(eq(id), any(MealCategoryModel.class))).thenReturn(null);

        // Act
        ResponseEntity<MealCategoryUpdateResponse> result = mealCategoryController.updateMealCategoryById(id,
                request);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
        assertNull(result.getBody());
    }

    @Test
    void WhenCalled_DeleteMealCategory_ShouldDeleteMealCategoryAndReturnNoContent() {
        // Arrange
        Long id = 1L;

        // Act
        ResponseEntity<Void> result = mealCategoryController.deleteMealCategory(id);

        // Assert
        verify(mealCategoryService).deleteMealCategoryById(id);
        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
        assertNull(result.getBody());
    }
}
