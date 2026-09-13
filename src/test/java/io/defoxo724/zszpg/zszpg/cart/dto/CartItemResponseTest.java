package io.defoxo724.zszpg.zszpg.cart.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import io.defoxo724.zszpg.zszpg.cart.CartModel;
import io.defoxo724.zszpg.zszpg.cart_item.CartItemModel;
import io.defoxo724.zszpg.zszpg.meal.MealModel;

class CartItemResponseTest {
    @Test
    void WhenCalled_FromModel_ShouldMapCartItemFields() {
        /* Arrange */
        MealModel meal = new MealModel(7L, "Pizza", "Margherita", new BigDecimal("32.00"), null);
        CartModel cart = new CartModel(1L, "No onions", new ArrayList<>());
        CartItemModel cartItem = new CartItemModel(3L, meal, cart, 2);

        /* Act */
        CartItemResponse result = CartItemResponse.fromModel(cartItem);

        /* Assert */
        assertEquals(new CartItemResponse(3L, 7L, 2), result);
    }
}
