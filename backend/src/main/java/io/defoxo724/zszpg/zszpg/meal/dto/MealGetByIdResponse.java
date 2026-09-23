package io.defoxo724.zszpg.zszpg.meal.dto;

import java.math.BigDecimal;

import io.defoxo724.zszpg.zszpg.meal.MealModel;
import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class MealGetByIdResponse {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private Long mealCategoryId;
    private String imageUrl;

    public MealGetByIdResponse(Long id, String name, String description, BigDecimal price, Long mealCategoryId) {
        this(id, name, description, price, mealCategoryId, null);
    }

    public static MealGetByIdResponse fromModel(MealModel meal) {
        return new MealGetByIdResponse(meal.getId(), meal.getName(), meal.getDescription(), meal.getPrice(),
                meal.getMealCategory().getId(), meal.getImageUrl());
    }

}
