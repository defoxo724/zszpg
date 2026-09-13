package io.defoxo724.zszpg.zszpg.cart_item;

import org.springframework.stereotype.Service;

import io.defoxo724.zszpg.zszpg.cart.CartModel;
import io.defoxo724.zszpg.zszpg.meal.MealModel;
import io.defoxo724.zszpg.zszpg.meal.MealRepository;

@Service
public class CartItemService {
    private final CartItemRepository cartItemRepository;
    private final MealRepository mealRepository;

    public CartItemService(CartItemRepository cartItemRepository, MealRepository mealRepository) {
        this.cartItemRepository = cartItemRepository;
        this.mealRepository = mealRepository;
    }

    public CartItemModel addMealToCart(CartModel cart, Long mealId) {
        if (mealId == null || cart == null || cart.getId() == null) {
            return null;
        }
        CartItemModel existingCartItem = cartItemRepository.findByCartIdAndMealId(cart.getId(), mealId).orElse(null);
        if (existingCartItem != null) {
            existingCartItem.setQuantity(existingCartItem.getQuantity() + 1);
            return cartItemRepository.save(existingCartItem);
        }
        MealModel meal = mealRepository.findById(mealId).orElse(null);
        if (meal == null) {
            return null;
        }
        CartItemModel cartItem = cartItemRepository.save(new CartItemModel(null, meal, cart, 1));
        cart.getCartItems().add(cartItem);
        return cartItem;
    }

    public boolean removeMealFromCart(CartModel cart, Long mealId) {
        if (mealId == null || cart == null || cart.getId() == null) {
            return false;
        }
        CartItemModel cartItem = cartItemRepository.findByCartIdAndMealId(cart.getId(), mealId).orElse(null);
        if (cartItem == null) {
            return false;
        }
        if (cartItem.getQuantity() > 1) {
            cartItem.setQuantity(cartItem.getQuantity() - 1);
            cartItemRepository.save(cartItem);
            return true;
        }
        cart.getCartItems().remove(cartItem);
        cartItemRepository.delete(cartItem);
        return true;
    }
}
