package io.defoxo724.zszpg.zszpg.meal_category;

import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MealCategoryService {
    private final MealCategoryRepository mealCategoryRepository;

    public MealCategoryService(MealCategoryRepository mealCategoryRepository) {
        this.mealCategoryRepository = mealCategoryRepository;
    }

    public MealCategoryModel createMealCategory(MealCategoryModel mealCategory) {
        return mealCategoryRepository.save(mealCategory);
    }

    public MealCategoryModel getMealCategoryById(Long id) {
        return mealCategoryRepository.findById(id).orElse(null);
    }

    public List<MealCategoryModel> getAllMealCategories() {
        return mealCategoryRepository.findAll();
    }

    public MealCategoryModel updateMealCategoryById(Long id, MealCategoryModel mealCategory) {
        MealCategoryModel existingMealCategory = mealCategoryRepository.findById(id).orElse(null);
        if (existingMealCategory == null) {
            return null;
        }
        existingMealCategory.setName(mealCategory.getName());
        return mealCategoryRepository.save(existingMealCategory);
    }

    public void deleteMealCategoryById(Long id) {
        if (mealCategoryRepository.findById(id).isEmpty()) {
            return;
        }
        mealCategoryRepository.deleteById(id);
    }

}
