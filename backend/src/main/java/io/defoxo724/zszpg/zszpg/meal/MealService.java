package io.defoxo724.zszpg.zszpg.meal;

import org.springframework.stereotype.Service;

import io.defoxo724.zszpg.zszpg.meal_category.MealCategoryModel;
import io.defoxo724.zszpg.zszpg.meal_category.MealCategoryRepository;

import java.util.List;

@Service
public class MealService {
    private final MealRepository mealRepository;
    private final MealCategoryRepository mealCategoryRepository;

    public MealService(MealRepository mealRepository, MealCategoryRepository mealCategoryRepository) {
        this.mealRepository = mealRepository;
        this.mealCategoryRepository = mealCategoryRepository;
    }

    public MealModel createMeal(MealModel meal, Long mealCategoryId) {
        if (mealCategoryId == null) {
            return null;
        }
        MealCategoryModel mealCategory = mealCategoryRepository.findById(mealCategoryId).orElse(null);
        if (mealCategory == null) {
            return null;
        }
        meal.setMealCategory(mealCategory);
        return mealRepository.save(meal);
    }

    public MealModel getMealById(Long id) {
        return mealRepository.findById(id).orElse(null);
    }

    public List<MealModel> getAllMeals() {
        return mealRepository.findAll();
    }

    public MealModel updateMealById(Long id, MealModel meal, Long mealCategoryId) {
        MealModel existingMeal = mealRepository.findById(id).orElse(null);
        if (existingMeal == null) {
            return null;
        }
        if (mealCategoryId == null) {
            return null;
        }
        MealCategoryModel mealCategory = mealCategoryRepository.findById(mealCategoryId).orElse(null);
        if (mealCategory == null) {
            return null;
        }
        existingMeal.setName(meal.getName());
        existingMeal.setDescription(meal.getDescription());
        existingMeal.setPrice(meal.getPrice());
        existingMeal.setMealCategory(mealCategory);
        return mealRepository.save(existingMeal);
    }

    public void deleteMealById(Long id) {
        if (mealRepository.findById(id).isEmpty()) {
            return;
        }
        mealRepository.deleteById(id);
    }

}
