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
public class MealGetAllResponseListElement {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private Long mealCategoryId;

    public static MealGetAllResponseListElement fromModel(MealModel meal) {
        return new MealGetAllResponseListElement(meal.getId(), meal.getName(), meal.getDescription(), meal.getPrice(),
                meal.getMealCategory().getId());
    }

}
