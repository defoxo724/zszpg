package io.defoxo724.zszpg.zszpg.cart.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import io.defoxo724.zszpg.zszpg.cart.CartModel;

class CartGetAllResponseTest {
    @Test
    void WhenCalled_FromModelList_ShouldMapEveryCart() {
        /* Arrange */
        List<CartModel> carts = List.of(
                new CartModel(1L, "No onions", new ArrayList<>()),
                new CartModel(2L, "Extra spicy", new ArrayList<>()));

        /* Act */
        CartGetAllResponse result = CartGetAllResponse.fromModelList(carts);

        /* Assert */
        assertEquals(List.of(
                new CartGetAllResponseListElement(1L, "No onions", new ArrayList<>()),
                new CartGetAllResponseListElement(2L, "Extra spicy", new ArrayList<>())), result.getCarts());
    }
}
