package io.defoxo724.zszpg.zszpg.cart;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import io.defoxo724.zszpg.zszpg.cart_item.CartItemModel;

class CartModelTest {
    @Test
    void WhenCalled_AllArgsConstructor_ShouldSetAllFields() {
        /* Arrange */
        List<CartItemModel> cartItems = new ArrayList<>();

        /* Act */
        CartModel cart = new CartModel(1L, "No onions", cartItems);

        /* Assert */
        assertEquals(1L, cart.getId());
        assertEquals("No onions", cart.getComment());
        assertSame(cartItems, cart.getCartItems());
    }
}
