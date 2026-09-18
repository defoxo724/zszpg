package io.defoxo724.zszpg.zszpg.cart.dto;

import java.util.ArrayList;
import java.util.List;

import io.defoxo724.zszpg.zszpg.cart_item.CartItemModel;

final class CartResponseMapper {
    private CartResponseMapper() {
    }

    static List<CartItemResponse> mapCartItems(List<CartItemModel> cartItems) {
        List<CartItemResponse> response = new ArrayList<>();
        for (CartItemModel cartItem : cartItems) {
            response.add(CartItemResponse.fromModel(cartItem));
        }
        return response;
    }
}
