package io.defoxo724.zszpg.zszpg.cart_item;

import io.defoxo724.zszpg.zszpg.cart.CartModel;
import io.defoxo724.zszpg.zszpg.meal.MealModel;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = { "cart_id", "meal_id" }))
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CartItemModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "meal_id", nullable = false)
    private MealModel meal;

    @ManyToOne(optional = false)
    @JoinColumn(name = "cart_id", nullable = false)
    private CartModel cart;

    private Integer quantity;
}
