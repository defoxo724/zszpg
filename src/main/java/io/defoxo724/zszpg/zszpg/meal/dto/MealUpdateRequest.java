package io.defoxo724.zszpg.zszpg.meal.dto;

import java.math.BigDecimal;

import io.defoxo724.zszpg.zszpg.meal.MealModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MealUpdateRequest {
    private String name;
    private String description;
    private BigDecimal price;

    public MealModel toModel() {
        return new MealModel(null, name, description, price);
    }
}
