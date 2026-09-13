package io.defoxo724.zszpg.zszpg.cart_item;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.math.BigDecimal;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import io.defoxo724.zszpg.zszpg.cart.CartModel;
import io.defoxo724.zszpg.zszpg.meal.MealModel;

class CartItemModelTest {
    @Test
    void WhenCalled_AllArgsConstructor_ShouldSetAllFields() {
        /* Arrange */
        MealModel meal = new MealModel(7L, "Pizza", "Margherita", new BigDecimal("32.00"), null);
        CartModel cart = new CartModel(1L, "No onions", new ArrayList<>());

        /* Act */
        CartItemModel cartItem = new CartItemModel(3L, meal, cart, 2);

        /* Assert */
        assertEquals(3L, cartItem.getId());
        assertSame(meal, cartItem.getMeal());
        assertSame(cart, cartItem.getCart());
        assertEquals(2, cartItem.getQuantity());
    }
}
