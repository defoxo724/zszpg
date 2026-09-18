package io.defoxo724.zszpg.zszpg.cart.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import io.defoxo724.zszpg.zszpg.cart.CartModel;

class CartUpdateRequestTest {
    @Test
    void WhenCalled_ToModel_ShouldMapRequestFields() {
        /* Arrange */
        CartUpdateRequest request = new CartUpdateRequest("Extra spicy");

        /* Act */
        CartModel result = request.toModel();

        /* Assert */
        assertNull(result.getId());
        assertEquals("Extra spicy", result.getComment());
        assertTrue(result.getCartItems().isEmpty());
    }
}
