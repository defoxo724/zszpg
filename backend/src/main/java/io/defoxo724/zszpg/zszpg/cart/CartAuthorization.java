package io.defoxo724.zszpg.zszpg.cart;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import io.defoxo724.zszpg.zszpg.user.UserModel;
import io.defoxo724.zszpg.zszpg.user.UserType;

@Component("cartAuthorization")
public class CartAuthorization {
    private final CartRepository cartRepository;

    public CartAuthorization(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    public boolean canAccess(Long cartId, Authentication authentication) {
        if (authentication == null || !(authentication.getPrincipal() instanceof UserModel user)) {
            return false;
        }
        if (user.getType() == UserType.ADMIN) {
            return true;
        }
        return cartRepository.findById(cartId)
                .map(cart -> cart.getOwner().getId().equals(user.getId()))
                .orElse(false);
    }
}
