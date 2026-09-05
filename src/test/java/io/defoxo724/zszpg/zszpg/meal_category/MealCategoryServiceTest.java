package io.defoxo724.zszpg.zszpg.meal_category;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
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
public class MealCategoryServiceTest {

    @Mock
    private MealCategoryRepository mealCategoryRepository;

    @InjectMocks
    private MealCategoryService mealCategoryService;

    @Test
    void CreateModel_ShouldSaveAndReturnSavedModel() {
        // Arrange
        MealCategoryModel mealCategory = new MealCategoryModel(null, "name");
        MealCategoryModel expectedResult = new MealCategoryModel(1L, "name");

        when(mealCategoryRepository.save(mealCategory)).thenReturn(expectedResult);

        // Act
        MealCategoryModel actualResult = mealCategoryService.createMealCategory(mealCategory);

        // Assert
        assertEquals(expectedResult, actualResult);
        verify(mealCategoryRepository, times(1)).save(mealCategory);
    }

    @Test
    void GetMealCategoryById_WhenModelWithGivenIdExists_ShouldReturnModel() {
        // Arrange
        MealCategoryModel expectedResult = new MealCategoryModel(1L, "name");

        when(mealCategoryRepository.findById(1L)).thenReturn(Optional.of(expectedResult));

        // Act
        MealCategoryModel actualResult = mealCategoryService.getMealCategoryById(1L);

        // Assert
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void GetMealCategoryById_WhenModelWithGivenIdDoesNotExist_ShouldReturnNull() {
        // Arrange
        when(mealCategoryRepository.findById(1L)).thenReturn(Optional.empty());

        // Act
        MealCategoryModel actualResult = mealCategoryService.getMealCategoryById(1L);

        // Assert
        assertNull(actualResult);
    }

    @Test
    void GetAllMealCategories_ShouldReturnListOfMealCategories() {
        // Arrange
        List<MealCategoryModel> expectedResult = List.of(
                new MealCategoryModel(1L, "name1"),
                new MealCategoryModel(2L, "name2"));

        when(mealCategoryRepository.findAll()).thenReturn(expectedResult);

        // Act
        List<MealCategoryModel> actualResult = mealCategoryService.getAllMealCategories();

        // Assert
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void UpdateMealCategoryById_WhenModelWithGivenIdExists_ShouldUpdateAndReturnUpdatedModel() {
        // Arrange
        MealCategoryModel existingMealCategory = new MealCategoryModel(1L, "name");
        MealCategoryModel updatedMealCategory = new MealCategoryModel(null, "new name");

        when(mealCategoryRepository.findById(1L)).thenReturn(Optional.of(existingMealCategory));
        when(mealCategoryRepository.save(existingMealCategory)).thenReturn(updatedMealCategory);

        // Act
        MealCategoryModel actualResult = mealCategoryService.updateMealCategoryById(1L, updatedMealCategory);

        // Assert
        assertEquals(updatedMealCategory, actualResult);
        verify(mealCategoryRepository, times(1)).findById(1L);
        verify(mealCategoryRepository, times(1)).save(existingMealCategory);
    }

    @Test
    void UpdateMealCategoryById_WhenModelWithGivenIdDoesNotExist_ShouldReturnNull() {
        // Arrange
        MealCategoryModel updatedMealCategory = new MealCategoryModel(null, "new name");
        when(mealCategoryRepository.findById(1L)).thenReturn(Optional.empty());

        // Act
        MealCategoryModel actualResult = mealCategoryService.updateMealCategoryById(1L, updatedMealCategory);

        // Assert
        assertNull(actualResult);
        verify(mealCategoryRepository, times(1)).findById(1L);
        verify(mealCategoryRepository, times(0)).save(any(MealCategoryModel.class));
    }

    @Test
    void DeleteMealCategoryById_WhenModelWithGivenIdExists_ShouldDeleteModel() {
        // Arrange
        MealCategoryModel existingMealCategory = new MealCategoryModel(1L, "name");
        when(mealCategoryRepository.findById(1L)).thenReturn(Optional.of(existingMealCategory));

        // Act
        mealCategoryService.deleteMealCategoryById(1L);

        // Assert
        verify(mealCategoryRepository, times(1)).findById(1L);
        verify(mealCategoryRepository, times(1)).deleteById(1L);
    }

    @Test
    void DeleteMealCategoryById_WhenModelWithGivenIdDoesNotExist_ShouldNotDeleteModel() {
        // Arrange
        when(mealCategoryRepository.findById(1L)).thenReturn(Optional.empty());

        // Act
        mealCategoryService.deleteMealCategoryById(1L);

        // Assert
        verify(mealCategoryRepository, times(1)).findById(1L);
        verify(mealCategoryRepository, times(0)).deleteById(anyLong());
    }
}
