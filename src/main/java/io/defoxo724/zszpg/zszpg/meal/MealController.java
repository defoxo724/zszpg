package io.defoxo724.zszpg.zszpg.meal;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;

import io.defoxo724.zszpg.zszpg.meal.dto.MealCreateRequest;
import io.defoxo724.zszpg.zszpg.meal.dto.MealCreateResponse;
import io.defoxo724.zszpg.zszpg.meal.dto.MealGetByIdResponse;
import io.defoxo724.zszpg.zszpg.meal.dto.MealUpdateResponse;
import io.defoxo724.zszpg.zszpg.meal.dto.MealUpdateRequest;
import io.defoxo724.zszpg.zszpg.meal.dto.MealGetAllResponse;

import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/meals")
public class MealController {
    private final MealService mealService;

    public MealController(MealService mealService) {
        this.mealService = mealService;
    }

    @PostMapping
    public ResponseEntity<MealCreateResponse> createMeal(@RequestBody MealCreateRequest meal) {
        MealModel createdMeal = mealService.createMeal(meal.toModel(), meal.getMealCategoryId());
        if (createdMeal == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(MealCreateResponse.fromModel(createdMeal));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MealGetByIdResponse> getMealById(@PathVariable Long id) {
        MealModel meal = mealService.getMealById(id);
        if (meal == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(MealGetByIdResponse.fromModel(meal));
    }

    @GetMapping
    public ResponseEntity<MealGetAllResponse> getAllMeals() {
        return ResponseEntity.ok(MealGetAllResponse.fromModelList(mealService.getAllMeals()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MealUpdateResponse> updateMealById(@PathVariable Long id,
            @RequestBody MealUpdateRequest meal) {
        MealModel updatedMeal = mealService.updateMealById(id, meal.toModel(), meal.getMealCategoryId());
        if (updatedMeal == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(MealUpdateResponse.fromModel(updatedMeal));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMeal(@PathVariable Long id) {
        mealService.deleteMealById(id);
        return ResponseEntity.noContent().build();
    }
}
