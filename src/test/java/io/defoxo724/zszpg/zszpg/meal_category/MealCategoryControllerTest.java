package io.defoxo724.zszpg.zszpg.meal_category;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
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
class MealCategoryControllerTest {
    @Mock
    private MealCategoryService mealCategoryService;
    @InjectMocks
    private MealCategoryController mealCategoryController;

    @Test
    void WhenCalled_CreateMealCategory_ShouldReturnCreatedCategory() {
        /* Arrange */
        MealCategoryCreateRequest request = new MealCategoryCreateRequest("Dinner");
        when(mealCategoryService.createMealCategory(any(MealCategoryModel.class)))
                .thenReturn(new MealCategoryModel(1L, "Dinner"));

        /* Act */
        ResponseEntity<MealCategoryCreateResponse> result = mealCategoryController.createMealCategory(request);

        /* Assert */
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(new MealCategoryCreateResponse(1L, "Dinner"), result.getBody());
    }

    @Test
    void WhenCalled_GetMealCategoryById_WithExistingCategory_ShouldReturnCategory() {
        /* Arrange */
        when(mealCategoryService.getMealCategoryById(1L)).thenReturn(new MealCategoryModel(1L, "Dinner"));

        /* Act */
        ResponseEntity<MealCategoryGetByIdResponse> result = mealCategoryController.getMealCategoryById(1L);

        /* Assert */
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(new MealCategoryGetByIdResponse(1L, "Dinner"), result.getBody());
    }

    @Test
    void WhenCalled_GetMealCategoryById_WithMissingCategory_ShouldReturnNotFound() {
        /* Arrange */
        when(mealCategoryService.getMealCategoryById(1L)).thenReturn(null);

        /* Act */
        ResponseEntity<MealCategoryGetByIdResponse> result = mealCategoryController.getMealCategoryById(1L);

        /* Assert */
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
        assertNull(result.getBody());
    }

    @Test
    void WhenCalled_GetAllMealCategories_ShouldReturnAllCategories() {
        /* Arrange */
        List<MealCategoryModel> categories = List.of(new MealCategoryModel(1L, "Dinner"));
        when(mealCategoryService.getAllMealCategories()).thenReturn(categories);

        /* Act */
        ResponseEntity<MealCategoryGetAllResponse> result = mealCategoryController.getAllMealCategories();

        /* Assert */
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(MealCategoryGetAllResponse.fromModelList(categories), result.getBody());
    }

    @Test
    void WhenCalled_UpdateMealCategoryById_WithExistingCategory_ShouldReturnUpdatedCategory() {
        /* Arrange */
        MealCategoryUpdateRequest request = new MealCategoryUpdateRequest("Lunch");
        when(mealCategoryService.updateMealCategoryById(org.mockito.ArgumentMatchers.eq(1L), any(MealCategoryModel.class)))
                .thenReturn(new MealCategoryModel(1L, "Lunch"));

        /* Act */
        ResponseEntity<MealCategoryUpdateResponse> result = mealCategoryController.updateMealCategoryById(1L, request);

        /* Assert */
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(new MealCategoryUpdateResponse(1L, "Lunch"), result.getBody());
    }

    @Test
    void WhenCalled_UpdateMealCategoryById_WithMissingCategory_ShouldReturnNotFound() {
        /* Arrange */
        MealCategoryUpdateRequest request = new MealCategoryUpdateRequest("Lunch");
        when(mealCategoryService.updateMealCategoryById(org.mockito.ArgumentMatchers.eq(1L), any(MealCategoryModel.class)))
                .thenReturn(null);

        /* Act */
        ResponseEntity<MealCategoryUpdateResponse> result = mealCategoryController.updateMealCategoryById(1L, request);

        /* Assert */
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
        assertNull(result.getBody());
    }

    @Test
    void WhenCalled_DeleteMealCategory_ShouldReturnNoContent() {
        /* Arrange */
        Long id = 1L;

        /* Act */
        ResponseEntity<Void> result = mealCategoryController.deleteMealCategory(id);

        /* Assert */
        verify(mealCategoryService).deleteMealCategoryById(id);
        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
        assertNull(result.getBody());
    }
}
