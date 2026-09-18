package io.defoxo724.zszpg.zszpg.cart.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import io.defoxo724.zszpg.zszpg.cart.CartModel;

class AddMealToCartResponseTest {
    @Test
    void WhenCalled_FromModel_ShouldMapCartFields() {
        /* Arrange */
        CartModel cart = new CartModel(1L, "No onions", new ArrayList<>());

        /* Act */
        AddMealToCartResponse result = AddMealToCartResponse.fromModel(cart);

        /* Assert */
        assertEquals(new AddMealToCartResponse(1L, "No onions", new ArrayList<>()), result);
    }
}
