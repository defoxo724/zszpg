package io.defoxo724.zszpg.zszpg.meal_category;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MealCategoryServiceTest {
    @Mock
    private MealCategoryRepository mealCategoryRepository;
    @InjectMocks
    private MealCategoryService mealCategoryService;

    @Test
    void WhenCalled_CreateMealCategory_ShouldReturnSavedCategory() {
        /* Arrange */
        MealCategoryModel category = new MealCategoryModel(null, "Dinner");
        MealCategoryModel savedCategory = new MealCategoryModel(1L, "Dinner");
        when(mealCategoryRepository.save(category)).thenReturn(savedCategory);

        /* Act */
        MealCategoryModel result = mealCategoryService.createMealCategory(category);

        /* Assert */
        assertSame(savedCategory, result);
        verify(mealCategoryRepository).save(category);
    }

    @Test
    void WhenCalled_GetMealCategoryById_WithExistingCategory_ShouldReturnCategory() {
        /* Arrange */
        MealCategoryModel expected = new MealCategoryModel(1L, "Dinner");
        when(mealCategoryRepository.findById(1L)).thenReturn(Optional.of(expected));

        /* Act */
        MealCategoryModel result = mealCategoryService.getMealCategoryById(1L);

        /* Assert */
        assertSame(expected, result);
    }

    @Test
    void WhenCalled_GetMealCategoryById_WithMissingCategory_ShouldReturnNull() {
        /* Arrange */
        when(mealCategoryRepository.findById(1L)).thenReturn(Optional.empty());

        /* Act */
        MealCategoryModel result = mealCategoryService.getMealCategoryById(1L);

        /* Assert */
        assertNull(result);
    }

    @Test
    void WhenCalled_GetAllMealCategories_ShouldReturnRepositoryResult() {
        /* Arrange */
        List<MealCategoryModel> expected = List.of(new MealCategoryModel(1L, "Dinner"));
        when(mealCategoryRepository.findAll()).thenReturn(expected);

        /* Act */
        List<MealCategoryModel> result = mealCategoryService.getAllMealCategories();

        /* Assert */
        assertSame(expected, result);
    }

    @Test
    void WhenCalled_UpdateMealCategoryById_WithExistingCategory_ShouldUpdateAndReturnSavedCategory() {
        /* Arrange */
        MealCategoryModel existing = new MealCategoryModel(1L, "Old");
        MealCategoryModel changes = new MealCategoryModel(null, "New");
        when(mealCategoryRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(mealCategoryRepository.save(existing)).thenReturn(existing);

        /* Act */
        MealCategoryModel result = mealCategoryService.updateMealCategoryById(1L, changes);

        /* Assert */
        assertSame(existing, result);
        assertEquals("New", existing.getName());
        verify(mealCategoryRepository).save(existing);
    }

    @Test
    void WhenCalled_UpdateMealCategoryById_WithMissingCategory_ShouldReturnNull() {
        /* Arrange */
        MealCategoryModel changes = new MealCategoryModel(null, "New");
        when(mealCategoryRepository.findById(1L)).thenReturn(Optional.empty());

        /* Act */
        MealCategoryModel result = mealCategoryService.updateMealCategoryById(1L, changes);

        /* Assert */
        assertNull(result);
        verify(mealCategoryRepository, never()).save(org.mockito.ArgumentMatchers.any());
    }

    @Test
    void WhenCalled_DeleteMealCategoryById_WithExistingCategory_ShouldDeleteCategory() {
        /* Arrange */
        when(mealCategoryRepository.findById(1L)).thenReturn(Optional.of(new MealCategoryModel(1L, "Dinner")));

        /* Act */
        mealCategoryService.deleteMealCategoryById(1L);

        /* Assert */
        verify(mealCategoryRepository).deleteById(1L);
    }

    @Test
    void WhenCalled_DeleteMealCategoryById_WithMissingCategory_ShouldNotDeleteCategory() {
        /* Arrange */
        when(mealCategoryRepository.findById(1L)).thenReturn(Optional.empty());

        /* Act */
        mealCategoryService.deleteMealCategoryById(1L);

        /* Assert */
        verify(mealCategoryRepository, never()).deleteById(1L);
    }
}
