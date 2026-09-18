package io.defoxo724.zszpg.zszpg.cart.dto;

import java.util.ArrayList;
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
public class CartGetAllResponse {
    private List<CartGetAllResponseListElement> carts;

    public static CartGetAllResponse fromModelList(List<CartModel> carts) {
        List<CartGetAllResponseListElement> cartList = new ArrayList<>();
        for (CartModel cart : carts) {
            cartList.add(CartGetAllResponseListElement.fromModel(cart));
        }
        return new CartGetAllResponse(cartList);
    }
}
