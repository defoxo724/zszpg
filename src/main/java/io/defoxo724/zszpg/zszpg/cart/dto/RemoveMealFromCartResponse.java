package io.defoxo724.zszpg.zszpg.cart.dto;

import java.util.List;

import io.defoxo724.zszpg.zszpg.cart.CartModel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class RemoveMealFromCartResponse {
    private Long id;
    private String comment;
    private List<CartItemResponse> cartItems;

    public static RemoveMealFromCartResponse fromModel(CartModel cart) {
        return new RemoveMealFromCartResponse(cart.getId(), cart.getComment(),
                CartResponseMapper.mapCartItems(cart.getCartItems()));
    }
}
