package io.defoxo724.zszpg.zszpg.meal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
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

@ExtendWith(MockitoExtension.class)
public class MealServiceTest {

    @Mock
    private MealRepository mealRepository;

    @InjectMocks
    private MealService mealService;

    @Test
    void CreateModel_ShouldSaveAndReturnSavedModel() {
        // Arrange
        MealModel meal = new MealModel(null, "name", "desc", new BigDecimal("12.99"));
        MealModel expectedResult = new MealModel(1L, "name", "desc", new BigDecimal("12.99"));

        when(mealRepository.save(meal)).thenReturn(expectedResult);

        // Act
        MealModel actualResult = mealService.createMeal(meal);

        // Assert
        assertEquals(expectedResult, actualResult);
        verify(mealRepository, times(1)).save(meal);
    }

    @Test
    void GetMealById_WhenModelWithGivenIdExists_ShouldReturnModel() {
        // Arrange
        MealModel expectedResult = new MealModel(1L, "name", "desc", new BigDecimal("12.99"));

        when(mealRepository.findById(1L)).thenReturn(Optional.of(expectedResult));

        // Act
        MealModel actualResult = mealService.getMealById(1L);

        // Assert
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void GetMealById_WhenModelWithGivenIdDoesNotExist_ShouldReturnNull() {
        // Arrange
        when(mealRepository.findById(1L)).thenReturn(Optional.empty());

        // Act
        MealModel actualResult = mealService.getMealById(1L);

        // Assert
        assertNull(actualResult);
    }

    @Test
    void GetAllMeals_ShouldReturnListOfMeals() {
        // Arrange
        List<MealModel> expectedResult = List.of(
                new MealModel(1L, "name1", "desc1", new BigDecimal("12.99")),
                new MealModel(2L, "name2", "desc2", new BigDecimal("15.99")));

        when(mealRepository.findAll()).thenReturn(expectedResult);

        // Act
        List<MealModel> actualResult = mealService.getAllMeals();

        // Assert
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void UpdateMealById_WhenModelWithGivenIdExists_ShouldUpdateAndReturnUpdatedModel() {
        // Arrange
        MealModel existingMeal = new MealModel(1L, "name", "desc", new BigDecimal("12.99"));
        MealModel updatedMeal = new MealModel(null, "new name", "new desc", new BigDecimal("15.99"));

        when(mealRepository.findById(1L)).thenReturn(Optional.of(existingMeal));
        when(mealRepository.save(existingMeal)).thenReturn(updatedMeal);

        // Act
        MealModel actualResult = mealService.updateMealById(1L, updatedMeal);

        // Assert
        assertEquals(updatedMeal, actualResult);
        verify(mealRepository, times(1)).findById(1L);
        verify(mealRepository, times(1)).save(existingMeal);
    }

    @Test
    void UpdateMealById_WhenModelWithGivenIdDoesNotExist_ShouldReturnNull() {
        // Arrange
        MealModel updatedMeal = new MealModel(null, "new name", "new desc", new BigDecimal("15.99"));
        when(mealRepository.findById(1L)).thenReturn(Optional.empty());

        // Act
        MealModel actualResult = mealService.updateMealById(1L, updatedMeal);

        // Assert
        assertNull(actualResult);
        verify(mealRepository, times(1)).findById(1L);
        verify(mealRepository, times(0)).save(any(MealModel.class));
    }

    @Test
    void DeleteMealById_WhenModelWithGivenIdExists_ShouldDeleteModel() {
        // Arrange
        MealModel existingMeal = new MealModel(1L, "name", "desc", new BigDecimal("12.99"));
        when(mealRepository.findById(1L)).thenReturn(Optional.of(existingMeal));

        // Act
        mealService.deleteMealById(1L);

        // Assert
        verify(mealRepository, times(1)).findById(1L);
        verify(mealRepository, times(1)).deleteById(1L);
    }

    @Test
    void DeleteMealById_WhenModelWithGivenIdDoesNotExist_ShouldNotDeleteModel() {
        // Arrange
        when(mealRepository.findById(1L)).thenReturn(Optional.empty());

        // Act
        mealService.deleteMealById(1L);

        // Assert
        verify(mealRepository, times(1)).findById(1L);
        verify(mealRepository, times(0)).deleteById(anyLong());
    }
}
