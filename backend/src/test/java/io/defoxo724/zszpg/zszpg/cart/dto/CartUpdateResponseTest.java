package io.defoxo724.zszpg.zszpg.cart.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import io.defoxo724.zszpg.zszpg.cart.CartModel;

class CartUpdateResponseTest {
    @Test
    void WhenCalled_FromModel_ShouldMapCartFields() {
        /* Arrange */
        CartModel cart = new CartModel(1L, "Extra spicy", new ArrayList<>());

        /* Act */
        CartUpdateResponse result = CartUpdateResponse.fromModel(cart);

        /* Assert */
        assertEquals(new CartUpdateResponse(1L, "Extra spicy", new ArrayList<>()), result);
    }
}
