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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.security.access.prepost.PreAuthorize;

@RestController
@RequestMapping("/meals")
@Tag(name = "Meals", description = "Manages meals and their assignment to meal categories.")
public class MealController {
    private final MealService mealService;

    public MealController(MealService mealService) {
        this.mealService = mealService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Create a meal", description = "Creates a new meal and assigns it to the category identified by mealCategoryId.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "The meal was created.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MealCreateResponse.class), examples = @ExampleObject(value = "{\"id\":1,\"name\":\"Margherita pizza\",\"description\":\"Pizza with tomatoes, mozzarella, and basil\",\"price\":29.90,\"mealCategoryId\":2}"))),
            @ApiResponse(responseCode = "404", description = "The category identified by mealCategoryId does not exist.", content = @Content)
    })
    public ResponseEntity<MealCreateResponse> createMeal(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "The new meal data and its category identifier.", required = true, content = @Content(schema = @Schema(implementation = MealCreateRequest.class), examples = @ExampleObject(value = "{\"name\":\"Margherita pizza\",\"description\":\"Pizza with tomatoes, mozzarella, and basil\",\"price\":29.90,\"mealCategoryId\":2}"))) @RequestBody MealCreateRequest meal) {
        MealModel createdMeal = mealService.createMeal(meal.toModel(), meal.getMealCategoryId());
        if (createdMeal == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(MealCreateResponse.fromModel(createdMeal));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ORDERER')")
    @Operation(summary = "Get a meal", description = "Returns the details of the meal with the specified identifier.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "The meal was found.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MealGetByIdResponse.class), examples = @ExampleObject(value = "{\"id\":1,\"name\":\"Margherita pizza\",\"description\":\"Pizza with tomatoes, mozzarella, and basil\",\"price\":29.90,\"mealCategoryId\":2}"))),
            @ApiResponse(responseCode = "404", description = "A meal with the specified identifier does not exist.", content = @Content)
    })
    public ResponseEntity<MealGetByIdResponse> getMealById(
            @Parameter(description = "The meal identifier.", example = "1", required = true) @PathVariable Long id) {
        MealModel meal = mealService.getMealById(id);
        if (meal == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(MealGetByIdResponse.fromModel(meal));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'ORDERER')")
    @Operation(summary = "Get all meals", description = "Returns all meals. When no meals exist, the meals field contains an empty list.")
    @ApiResponse(responseCode = "200", description = "The meal list was returned.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MealGetAllResponse.class), examples = @ExampleObject(value = "{\"meals\":[{\"id\":1,\"name\":\"Margherita pizza\",\"description\":\"Pizza with tomatoes, mozzarella, and basil\",\"price\":29.90,\"mealCategoryId\":2}]}")))
    public ResponseEntity<MealGetAllResponse> getAllMeals() {
        return ResponseEntity.ok(MealGetAllResponse.fromModelList(mealService.getAllMeals()));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Update a meal", description = "Replaces the data of the specified meal and assigns it to the category identified by mealCategoryId.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "The meal was updated.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MealUpdateResponse.class), examples = @ExampleObject(value = "{\"id\":1,\"name\":\"Pepperoni pizza\",\"description\":\"Pizza with tomatoes, mozzarella, and pepperoni\",\"price\":31.50,\"mealCategoryId\":2}"))),
            @ApiResponse(responseCode = "404", description = "The meal or the specified meal category does not exist.", content = @Content)
    })
    public ResponseEntity<MealUpdateResponse> updateMealById(
            @Parameter(description = "The identifier of the meal to update.", example = "1", required = true) @PathVariable Long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "The replacement meal data.", required = true, content = @Content(schema = @Schema(implementation = MealUpdateRequest.class), examples = @ExampleObject(value = "{\"name\":\"Pepperoni pizza\",\"description\":\"Pizza with tomatoes, mozzarella, and pepperoni\",\"price\":31.50,\"mealCategoryId\":2}"))) @RequestBody MealUpdateRequest meal) {
        MealModel updatedMeal = mealService.updateMealById(id, meal.toModel(), meal.getMealCategoryId());
        if (updatedMeal == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(MealUpdateResponse.fromModel(updatedMeal));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete a meal", description = "Deletes the meal with the specified identifier. The operation returns no response body.")
    @ApiResponse(responseCode = "204", description = "The delete request was completed.", content = @Content)
    public ResponseEntity<Void> deleteMeal(
            @Parameter(description = "The identifier of the meal to delete.", example = "1", required = true) @PathVariable Long id) {
        mealService.deleteMealById(id);
        return ResponseEntity.noContent().build();
    }
}
