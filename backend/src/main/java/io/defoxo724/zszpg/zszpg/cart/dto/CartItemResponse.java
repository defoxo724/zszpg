package io.defoxo724.zszpg.zszpg.cart.dto;

import io.defoxo724.zszpg.zszpg.cart_item.CartItemModel;
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
public class CartItemResponse {
    private Long id;
    private Long mealId;
    private Integer quantity;

    public static CartItemResponse fromModel(CartItemModel cartItem) {
        return new CartItemResponse(cartItem.getId(), cartItem.getMeal().getId(), cartItem.getQuantity());
    }
}
