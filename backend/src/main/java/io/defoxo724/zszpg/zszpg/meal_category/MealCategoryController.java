package io.defoxo724.zszpg.zszpg.meal_category;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Value;
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
@RequestMapping("/meal-categories")
@Tag(name = "Meal categories", description = "Manages categories used to group meals.")
public class MealCategoryController {
    private final MealCategoryService mealCategoryService;

    @Value("${app.meal_category.name.min_length:3}")
    private int nameMinLength = 3;
    @Value("${app.meal_category.name.max_length:100}")
    private int nameMaxLength = 100;

    public MealCategoryController(MealCategoryService mealCategoryService) {
        this.mealCategoryService = mealCategoryService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Create a meal category", description = "Creates a new meal category with the specified name.")
    @ApiResponse(responseCode = "200", description = "The meal category was created.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MealCategoryCreateResponse.class), examples = @ExampleObject(value = "{\"id\":2,\"name\":\"Main courses\"}")))
    public ResponseEntity<MealCategoryCreateResponse> createMealCategory(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "The new meal category data.", required = true, content = @Content(schema = @Schema(implementation = MealCategoryCreateRequest.class), examples = @ExampleObject(value = "{\"name\":\"Main courses\"}"))) @RequestBody MealCategoryCreateRequest mealCategory) {
        if (!isValidName(mealCategory.getName())) {
            return ResponseEntity.badRequest().build();
        }
        MealCategoryModel createdMealCategory = mealCategoryService.createMealCategory(mealCategory.toModel());
        return ResponseEntity.ok(MealCategoryCreateResponse.fromModel(createdMealCategory));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ORDERER')")
    @Operation(summary = "Get a meal category", description = "Returns the meal category with the specified identifier.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "The meal category was found.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MealCategoryGetByIdResponse.class), examples = @ExampleObject(value = "{\"id\":2,\"name\":\"Main courses\"}"))),
            @ApiResponse(responseCode = "404", description = "A meal category with the specified identifier does not exist.", content = @Content)
    })
    public ResponseEntity<MealCategoryGetByIdResponse> getMealCategoryById(
            @Parameter(description = "The meal category identifier.", example = "2", required = true) @PathVariable Long id) {
        MealCategoryModel mealCategory = mealCategoryService.getMealCategoryById(id);
        if (mealCategory == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(MealCategoryGetByIdResponse.fromModel(mealCategory));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'ORDERER')")
    @Operation(summary = "Get all meal categories", description = "Returns all meal categories. When no categories exist, the mealCategories field contains an empty list.")
    @ApiResponse(responseCode = "200", description = "The meal category list was returned.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MealCategoryGetAllResponse.class), examples = @ExampleObject(value = "{\"mealCategories\":[{\"id\":2,\"name\":\"Main courses\"}]}")))
    public ResponseEntity<MealCategoryGetAllResponse> getAllMealCategories() {
        return ResponseEntity
                .ok(MealCategoryGetAllResponse.fromModelList(mealCategoryService.getAllMealCategories()));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Update a meal category", description = "Replaces the data of the meal category with the specified identifier.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "The meal category was updated.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MealCategoryUpdateResponse.class), examples = @ExampleObject(value = "{\"id\":2,\"name\":\"Lunch dishes\"}"))),
            @ApiResponse(responseCode = "404", description = "A meal category with the specified identifier does not exist.", content = @Content)
    })
    public ResponseEntity<MealCategoryUpdateResponse> updateMealCategoryById(
            @Parameter(description = "The identifier of the meal category to update.", example = "2", required = true) @PathVariable Long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "The replacement meal category data.", required = true, content = @Content(schema = @Schema(implementation = MealCategoryUpdateRequest.class), examples = @ExampleObject(value = "{\"name\":\"Lunch dishes\"}"))) @RequestBody MealCategoryUpdateRequest mealCategory) {
        if (!isValidName(mealCategory.getName())) {
            return ResponseEntity.badRequest().build();
        }
        MealCategoryModel updatedMealCategory = mealCategoryService.updateMealCategoryById(id,
                mealCategory.toModel());
        if (updatedMealCategory == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(MealCategoryUpdateResponse.fromModel(updatedMealCategory));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete a meal category", description = "Deletes the meal category with the specified identifier. The operation returns no response body.")
    @ApiResponse(responseCode = "204", description = "The delete request was completed.", content = @Content)
    public ResponseEntity<Void> deleteMealCategory(
            @Parameter(description = "The identifier of the meal category to delete.", example = "2", required = true) @PathVariable Long id) {
        mealCategoryService.deleteMealCategoryById(id);
        return ResponseEntity.noContent().build();
    }

    private boolean isValidName(String name) {
        return name != null && !name.isBlank() && name.length() >= nameMinLength && name.length() <= nameMaxLength;
    }
}
