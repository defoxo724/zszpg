package io.defoxo724.zszpg.zszpg.meal_category;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;

import io.defoxo724.zszpg.zszpg.meal_category.dto.MealCategoryCreateRequest;
import io.defoxo724.zszpg.zszpg.meal_category.dto.MealCategoryCreateResponse;
import io.defoxo724.zszpg.zszpg.meal_category.dto.MealCategoryGetByIdResponse;
import io.defoxo724.zszpg.zszpg.meal_category.dto.MealCategoryUpdateResponse;
import io.defoxo724.zszpg.zszpg.meal_category.dto.MealCategoryUpdateRequest;
import io.defoxo724.zszpg.zszpg.meal_category.dto.MealCategoryGetAllResponse;

import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/meal-categories")
public class MealCategoryController {
    private final MealCategoryService mealCategoryService;

    public MealCategoryController(MealCategoryService mealCategoryService) {
        this.mealCategoryService = mealCategoryService;
    }

    @PostMapping
    public ResponseEntity<MealCategoryCreateResponse> createMealCategory(
            @RequestBody MealCategoryCreateRequest mealCategory) {
        MealCategoryModel createdMealCategory = mealCategoryService.createMealCategory(mealCategory.toModel());
        return ResponseEntity.ok(MealCategoryCreateResponse.fromModel(createdMealCategory));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MealCategoryGetByIdResponse> getMealCategoryById(@PathVariable Long id) {
        MealCategoryModel mealCategory = mealCategoryService.getMealCategoryById(id);
        if (mealCategory == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(MealCategoryGetByIdResponse.fromModel(mealCategory));
    }

    @GetMapping
    public ResponseEntity<MealCategoryGetAllResponse> getAllMealCategories() {
        return ResponseEntity
                .ok(MealCategoryGetAllResponse.fromModelList(mealCategoryService.getAllMealCategories()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MealCategoryUpdateResponse> updateMealCategoryById(@PathVariable Long id,
            @RequestBody MealCategoryUpdateRequest mealCategory) {
        MealCategoryModel updatedMealCategory = mealCategoryService.updateMealCategoryById(id,
                mealCategory.toModel());
        if (updatedMealCategory == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(MealCategoryUpdateResponse.fromModel(updatedMealCategory));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMealCategory(@PathVariable Long id) {
        mealCategoryService.deleteMealCategoryById(id);
        return ResponseEntity.noContent().build();
    }
}
