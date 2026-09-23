package io.defoxo724.zszpg.zszpg.meal;

import java.math.BigDecimal;

import io.defoxo724.zszpg.zszpg.meal_category.MealCategoryModel;
import jakarta.persistence.Entity;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MealModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    @Column(name = "image_url", length = 2048)
    private String imageUrl;
    @ManyToOne(optional = false)
    @JoinColumn(name = "meal_category_id", nullable = false)
    private MealCategoryModel mealCategory;

    public MealModel(Long id, String name, String description, BigDecimal price, MealCategoryModel mealCategory) {
        this(id, name, description, price, null, mealCategory);
    }
}
