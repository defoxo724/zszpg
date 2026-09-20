package io.defoxo724.zszpg.zszpg.cart;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;

import io.defoxo724.zszpg.zszpg.cart_item.CartItemModel;
import io.defoxo724.zszpg.zszpg.cart_item.CartItemService;
import io.defoxo724.zszpg.zszpg.user.UserModel;

@Service
public class CartService {
    private final CartRepository cartRepository;
    private final CartItemService cartItemService;

    public CartService(CartRepository cartRepository, CartItemService cartItemService) {
        this.cartRepository = cartRepository;
        this.cartItemService = cartItemService;
    }

    public CartModel createCartForUser(UserModel user) {
        CartModel cart = new CartModel(null, "", new ArrayList<>(), user);
        return cartRepository.save(cart);
    }

    public CartModel getCurrentUserCart() {
        UserModel currentUser = getCurrentUser();
        if (currentUser == null) {
            return null;
        }
        return cartRepository.findByOwnerId(currentUser.getId()).orElse(null);
    }

    public CartModel getCartById(Long id) {
        return cartRepository.findById(id).orElse(null);
    }

    public List<CartModel> getAllCarts() {
        return cartRepository.findAll();
    }

    public CartModel updateCartById(Long id, CartModel cart) {
        CartModel existingCart = cartRepository.findById(id).orElse(null);
        if (existingCart == null) {
            return null;
        }
        existingCart.setComment(cart.getComment());
        return cartRepository.save(existingCart);
    }

    @Transactional
    public CartModel addMealToCart(Long cartId, Long mealId) {
        CartModel cart = cartRepository.findById(cartId).orElse(null);
        if (cart == null) {
            return null;
        }
        CartItemModel cartItem = cartItemService.addMealToCart(cart, mealId);
        if (cartItem == null) {
            return null;
        }
        return cart;
    }

    @Transactional
    public CartModel removeMealFromCart(Long cartId, Long mealId) {
        CartModel cart = cartRepository.findById(cartId).orElse(null);
        if (cart == null) {
            return null;
        }
        if (!cartItemService.removeMealFromCart(cart, mealId)) {
            return null;
        }
        return cart;
    }

    private UserModel getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !(authentication.getPrincipal() instanceof UserModel user)) {
            return null;
        }
        return user;
    }
}
