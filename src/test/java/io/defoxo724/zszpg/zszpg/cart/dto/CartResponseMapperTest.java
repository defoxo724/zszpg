package io.defoxo724.zszpg.zszpg.cart.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import io.defoxo724.zszpg.zszpg.cart.CartModel;
import io.defoxo724.zszpg.zszpg.cart_item.CartItemModel;
import io.defoxo724.zszpg.zszpg.meal.MealModel;

class CartResponseMapperTest {
    @Test
    void WhenCalled_MapCartItems_ShouldMapEveryCartItem() {
        /* Arrange */
        CartModel cart = new CartModel(1L, "No onions", new ArrayList<>());
        MealModel pizza = new MealModel(7L, "Pizza", "Margherita", new BigDecimal("32.00"), null);
        MealModel burger = new MealModel(8L, "Burger", "Cheeseburger", new BigDecimal("28.00"), null);
        List<CartItemModel> cartItems = List.of(
                new CartItemModel(3L, pizza, cart, 2),
                new CartItemModel(4L, burger, cart, 1));

        /* Act */
        List<CartItemResponse> result = CartResponseMapper.mapCartItems(cartItems);

        /* Assert */
        assertEquals(List.of(new CartItemResponse(3L, 7L, 2), new CartItemResponse(4L, 8L, 1)), result);
    }
}
