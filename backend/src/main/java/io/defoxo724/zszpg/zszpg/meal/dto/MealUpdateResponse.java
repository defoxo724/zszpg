package io.defoxo724.zszpg.zszpg.meal.dto;

import java.math.BigDecimal;

import io.defoxo724.zszpg.zszpg.meal.MealModel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class MealUpdateResponse {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private Long mealCategoryId;
    private String imageUrl;

    public MealUpdateResponse(Long id, String name, String description, BigDecimal price, Long mealCategoryId) {
        this(id, name, description, price, mealCategoryId, null);
    }

    public static MealUpdateResponse fromModel(MealModel meal) {
        return new MealUpdateResponse(meal.getId(), meal.getName(), meal.getDescription(), meal.getPrice(),
                meal.getMealCategory().getId(), meal.getImageUrl());
    }
}
