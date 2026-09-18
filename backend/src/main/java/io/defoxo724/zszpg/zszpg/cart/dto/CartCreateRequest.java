package io.defoxo724.zszpg.zszpg.cart.dto;

import java.util.ArrayList;

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
public class CartCreateRequest {
    private String comment;

    public CartModel toModel() {
        return new CartModel(null, comment, new ArrayList<>());
    }
}
