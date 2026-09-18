package io.defoxo724.zszpg.zszpg.cart.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import io.defoxo724.zszpg.zszpg.cart.CartModel;

class CartCreateRequestTest {
    @Test
    void WhenCalled_ToModel_ShouldMapRequestFields() {
        /* Arrange */
        CartCreateRequest request = new CartCreateRequest("No onions");

        /* Act */
        CartModel result = request.toModel();

        /* Assert */
        assertNull(result.getId());
        assertEquals("No onions", result.getComment());
        assertTrue(result.getCartItems().isEmpty());
    }
}
