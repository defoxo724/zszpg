package io.defoxo724.zszpg.zszpg.cart.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import io.defoxo724.zszpg.zszpg.cart.CartModel;

class CartCreateResponseTest {
    @Test
    void WhenCalled_FromModel_ShouldMapCartFields() {
        /* Arrange */
        CartModel cart = new CartModel(1L, "No onions", new ArrayList<>());

        /* Act */
        CartCreateResponse result = CartCreateResponse.fromModel(cart);

        /* Assert */
        assertEquals(new CartCreateResponse(1L, "No onions", new ArrayList<>()), result);
    }
}
