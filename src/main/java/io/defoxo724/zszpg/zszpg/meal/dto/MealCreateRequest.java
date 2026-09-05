package io.defoxo724.zszpg.zszpg.meal.dto;

import java.math.BigDecimal;

import io.defoxo724.zszpg.zszpg.meal.MealModel;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class MealCreateRequest {
    private String name;
    private String description;
    private BigDecimal price;

    public MealModel toModel() {
        return new MealModel(null, name, description, price);
    }
}
