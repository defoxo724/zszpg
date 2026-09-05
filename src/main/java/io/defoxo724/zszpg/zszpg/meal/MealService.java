package io.defoxo724.zszpg.zszpg.meal;

import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MealService {
    private final MealRepository mealRepository;

    public MealService(MealRepository mealRepository) {
        this.mealRepository = mealRepository;
    }

    public MealModel createMeal(MealModel meal) {
        return mealRepository.save(meal);
    }

    public MealModel getMealById(Long id) {
        return mealRepository.findById(id).orElse(null);
    }

    public List<MealModel> getAllMeals() {
        return mealRepository.findAll();
    }

    public MealModel updateMealById(Long id, MealModel meal) {
        MealModel existingMeal = mealRepository.findById(id).orElse(null);
        if (existingMeal == null) {
            return null;
        }
        existingMeal.setName(meal.getName());
        existingMeal.setDescription(meal.getDescription());
        existingMeal.setPrice(meal.getPrice());
        return mealRepository.save(existingMeal);
    }

    public void deleteMealById(Long id) {
        if (mealRepository.findById(id).isEmpty()) {
            return;
        }
        mealRepository.deleteById(id);
    }

}
