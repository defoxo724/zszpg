package io.defoxo724.zszpg.zszpg.meal_category;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import io.defoxo724.zszpg.zszpg.meal.MealImageStorageService;
import java.util.List;

@Service
public class MealCategoryService {
    private final MealCategoryRepository mealCategoryRepository;
    private final MealImageStorageService imageStorageService;

    public MealCategoryService(MealCategoryRepository mealCategoryRepository) {
        this(mealCategoryRepository, null);
    }

    @Autowired
    public MealCategoryService(MealCategoryRepository mealCategoryRepository,
            MealImageStorageService imageStorageService) {
        this.mealCategoryRepository = mealCategoryRepository;
        this.imageStorageService = imageStorageService;
    }

    public MealCategoryModel createMealCategory(MealCategoryModel mealCategory) {
        return createMealCategory(mealCategory, null);
    }

    public MealCategoryModel createMealCategory(MealCategoryModel mealCategory, MultipartFile image) {
        if (image != null && !image.isEmpty()) {
            mealCategory.setImageUrl(imageStorageService.store(image));
        }
        return mealCategoryRepository.save(mealCategory);
    }

    public MealCategoryModel getMealCategoryById(Long id) {
        return mealCategoryRepository.findById(id).orElse(null);
    }

    public List<MealCategoryModel> getAllMealCategories() {
        return mealCategoryRepository.findAll();
    }

    public MealCategoryModel updateMealCategoryById(Long id, MealCategoryModel mealCategory) {
        return updateMealCategoryById(id, mealCategory, null);
    }

    public MealCategoryModel updateMealCategoryById(Long id, MealCategoryModel mealCategory, MultipartFile image) {
        MealCategoryModel existingMealCategory = mealCategoryRepository.findById(id).orElse(null);
        if (existingMealCategory == null) {
            return null;
        }
        existingMealCategory.setName(mealCategory.getName());
        if (image != null && !image.isEmpty()) {
            existingMealCategory.setImageUrl(imageStorageService.store(image));
        }
        return mealCategoryRepository.save(existingMealCategory);
    }

    public void deleteMealCategoryById(Long id) {
        if (mealCategoryRepository.findById(id).isEmpty()) {
            return;
        }
        mealCategoryRepository.deleteById(id);
    }

}
