package io.defoxo724.zszpg.zszpg.meal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import io.defoxo724.zszpg.zszpg.meal_category.MealCategoryModel;
import io.defoxo724.zszpg.zszpg.meal_category.MealCategoryRepository;

@ExtendWith(MockitoExtension.class)
class MealServiceTest {
    @Mock
    private MealRepository mealRepository;
    @Mock
    private MealCategoryRepository mealCategoryRepository;
    @InjectMocks
    private MealService mealService;

    @Test
    void WhenCalled_CreateMeal_WithExistingCategory_ShouldAssignCategoryAndReturnSavedMeal() {
        /* Arrange */
        MealModel meal = meal(null, "Pizza", "Margherita", "32.00", null);
        MealCategoryModel category = new MealCategoryModel(5L, "Main course");
        MealModel savedMeal = meal(1L, "Pizza", "Margherita", "32.00", category);
        when(mealCategoryRepository.findById(5L)).thenReturn(Optional.of(category));
        when(mealRepository.save(meal)).thenReturn(savedMeal);

        /* Act */
        MealModel result = mealService.createMeal(meal, 5L);

        /* Assert */
        assertSame(savedMeal, result);
        assertSame(category, meal.getMealCategory());
        verify(mealRepository).save(meal);
    }

    @Test
    void WhenCalled_CreateMeal_WithNullCategoryId_ShouldReturnNull() {
        /* Arrange */
        MealModel meal = meal(null, "Pizza", "Margherita", "32.00", null);

        /* Act */
        MealModel result = mealService.createMeal(meal, null);

        /* Assert */
        assertNull(result);
        verify(mealCategoryRepository, never()).findById(org.mockito.ArgumentMatchers.anyLong());
        verify(mealRepository, never()).save(org.mockito.ArgumentMatchers.any());
    }

    @Test
    void WhenCalled_CreateMeal_WithMissingCategory_ShouldReturnNull() {
        /* Arrange */
        MealModel meal = meal(null, "Pizza", "Margherita", "32.00", null);
        when(mealCategoryRepository.findById(5L)).thenReturn(Optional.empty());

        /* Act */
        MealModel result = mealService.createMeal(meal, 5L);

        /* Assert */
        assertNull(result);
        verify(mealRepository, never()).save(org.mockito.ArgumentMatchers.any());
    }

    @Test
    void WhenCalled_GetMealById_WithExistingMeal_ShouldReturnMeal() {
        /* Arrange */
        MealModel expected = meal(1L, "Pizza", "Margherita", "32.00", null);
        when(mealRepository.findById(1L)).thenReturn(Optional.of(expected));

        /* Act */
        MealModel result = mealService.getMealById(1L);

        /* Assert */
        assertSame(expected, result);
    }

    @Test
    void WhenCalled_GetMealById_WithMissingMeal_ShouldReturnNull() {
        /* Arrange */
        when(mealRepository.findById(1L)).thenReturn(Optional.empty());

        /* Act */
        MealModel result = mealService.getMealById(1L);

        /* Assert */
        assertNull(result);
    }

    @Test
    void WhenCalled_GetAllMeals_ShouldReturnRepositoryResult() {
        /* Arrange */
        List<MealModel> expected = List.of(meal(1L, "Pizza", "Margherita", "32.00", null));
        when(mealRepository.findAll()).thenReturn(expected);

        /* Act */
        List<MealModel> result = mealService.getAllMeals();

        /* Assert */
        assertSame(expected, result);
    }

    @Test
    void WhenCalled_UpdateMealById_WithExistingMealAndCategory_ShouldUpdateAndReturnSavedMeal() {
        /* Arrange */
        MealCategoryModel oldCategory = new MealCategoryModel(2L, "Old");
        MealCategoryModel newCategory = new MealCategoryModel(5L, "New");
        MealModel existing = meal(1L, "Old", "Old description", "10.00", oldCategory);
        MealModel changes = meal(null, "New", "New description", "15.00", null);
        when(mealRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(mealCategoryRepository.findById(5L)).thenReturn(Optional.of(newCategory));
        when(mealRepository.save(existing)).thenReturn(existing);

        /* Act */
        MealModel result = mealService.updateMealById(1L, changes, 5L);

        /* Assert */
        assertSame(existing, result);
        assertEquals("New", existing.getName());
        assertEquals("New description", existing.getDescription());
        assertEquals(new BigDecimal("15.00"), existing.getPrice());
        assertSame(newCategory, existing.getMealCategory());
        verify(mealRepository).save(existing);
    }

    @Test
    void WhenCalled_UpdateMealById_WithMissingMeal_ShouldReturnNull() {
        /* Arrange */
        MealModel changes = meal(null, "New", "New description", "15.00", null);
        when(mealRepository.findById(1L)).thenReturn(Optional.empty());

        /* Act */
        MealModel result = mealService.updateMealById(1L, changes, 5L);

        /* Assert */
        assertNull(result);
        verify(mealCategoryRepository, never()).findById(5L);
        verify(mealRepository, never()).save(org.mockito.ArgumentMatchers.any());
    }

    @Test
    void WhenCalled_UpdateMealById_WithNullCategoryId_ShouldReturnNull() {
        /* Arrange */
        MealModel existing = meal(1L, "Old", "Old description", "10.00", null);
        MealModel changes = meal(null, "New", "New description", "15.00", null);
        when(mealRepository.findById(1L)).thenReturn(Optional.of(existing));

        /* Act */
        MealModel result = mealService.updateMealById(1L, changes, null);

        /* Assert */
        assertNull(result);
        verify(mealCategoryRepository, never()).findById(org.mockito.ArgumentMatchers.anyLong());
        verify(mealRepository, never()).save(org.mockito.ArgumentMatchers.any());
    }

    @Test
    void WhenCalled_UpdateMealById_WithMissingCategory_ShouldReturnNull() {
        /* Arrange */
        MealModel existing = meal(1L, "Old", "Old description", "10.00", null);
        MealModel changes = meal(null, "New", "New description", "15.00", null);
        when(mealRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(mealCategoryRepository.findById(5L)).thenReturn(Optional.empty());

        /* Act */
        MealModel result = mealService.updateMealById(1L, changes, 5L);

        /* Assert */
        assertNull(result);
        verify(mealRepository, never()).save(org.mockito.ArgumentMatchers.any());
    }

    @Test
    void WhenCalled_DeleteMealById_WithExistingMeal_ShouldDeleteMeal() {
        /* Arrange */
        when(mealRepository.findById(1L)).thenReturn(Optional.of(meal(1L, "Pizza", "Margherita", "32.00", null)));

        /* Act */
        mealService.deleteMealById(1L);

        /* Assert */
        verify(mealRepository).deleteById(1L);
    }

    @Test
    void WhenCalled_DeleteMealById_WithMissingMeal_ShouldNotDeleteMeal() {
        /* Arrange */
        when(mealRepository.findById(1L)).thenReturn(Optional.empty());

        /* Act */
        mealService.deleteMealById(1L);

        /* Assert */
        verify(mealRepository, never()).deleteById(1L);
    }

    private MealModel meal(Long id, String name, String description, String price, MealCategoryModel category) {
        return new MealModel(id, name, description, new BigDecimal(price), category);
    }
}
