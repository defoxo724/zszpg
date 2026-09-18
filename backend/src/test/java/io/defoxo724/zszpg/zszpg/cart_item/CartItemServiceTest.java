package io.defoxo724.zszpg.zszpg.cart_item;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import io.defoxo724.zszpg.zszpg.cart.CartModel;
import io.defoxo724.zszpg.zszpg.meal.MealModel;
import io.defoxo724.zszpg.zszpg.meal.MealRepository;

@ExtendWith(MockitoExtension.class)
class CartItemServiceTest {
    @Mock
    private CartItemRepository cartItemRepository;
    @Mock
    private MealRepository mealRepository;
    @InjectMocks
    private CartItemService cartItemService;

    @Test
    void WhenCalled_AddMealToCart_WithExistingItem_ShouldIncreaseQuantity() {
        /* Arrange */
        CartModel cart = cart();
        CartItemModel cartItem = cartItem(cart, 2);
        cart.getCartItems().add(cartItem);
        when(cartItemRepository.findByCartIdAndMealId(1L, 7L)).thenReturn(Optional.of(cartItem));
        when(cartItemRepository.save(cartItem)).thenReturn(cartItem);

        /* Act */
        CartItemModel result = cartItemService.addMealToCart(cart, 7L);

        /* Assert */
        assertSame(cartItem, result);
        assertEquals(3, cartItem.getQuantity());
        verify(cartItemRepository).save(cartItem);
        verify(mealRepository, never()).findById(7L);
    }

    @Test
    void WhenCalled_AddMealToCart_WithNewMeal_ShouldCreateCartItem() {
        /* Arrange */
        CartModel cart = cart();
        MealModel meal = meal();
        when(cartItemRepository.findByCartIdAndMealId(1L, 7L)).thenReturn(Optional.empty());
        when(mealRepository.findById(7L)).thenReturn(Optional.of(meal));
        when(cartItemRepository.save(any(CartItemModel.class))).thenAnswer(invocation -> {
            CartItemModel value = invocation.getArgument(0);
            value.setId(3L);
            return value;
        });

        /* Act */
        CartItemModel result = cartItemService.addMealToCart(cart, 7L);

        /* Assert */
        ArgumentCaptor<CartItemModel> captor = ArgumentCaptor.forClass(CartItemModel.class);
        verify(cartItemRepository).save(captor.capture());
        assertSame(meal, result.getMeal());
        assertSame(cart, result.getCart());
        assertEquals(1, result.getQuantity());
        assertEquals(1, cart.getCartItems().size());
        assertSame(result, cart.getCartItems().getFirst());
    }

    @Test
    void WhenCalled_AddMealToCart_WithMissingMeal_ShouldReturnNull() {
        /* Arrange */
        CartModel cart = cart();
        when(cartItemRepository.findByCartIdAndMealId(1L, 7L)).thenReturn(Optional.empty());
        when(mealRepository.findById(7L)).thenReturn(Optional.empty());

        /* Act */
        CartItemModel result = cartItemService.addMealToCart(cart, 7L);

        /* Assert */
        assertNull(result);
        assertTrue(cart.getCartItems().isEmpty());
        verify(cartItemRepository, never()).save(any());
    }

    @Test
    void WhenCalled_AddMealToCart_WithNullMealId_ShouldReturnNull() {
        /* Arrange */
        CartModel cart = cart();

        /* Act */
        CartItemModel result = cartItemService.addMealToCart(cart, null);

        /* Assert */
        assertNull(result);
        verify(cartItemRepository, never()).findByCartIdAndMealId(any(), any());
    }

    @Test
    void WhenCalled_AddMealToCart_WithNullCart_ShouldReturnNull() {
        /* Arrange */
        Long mealId = 7L;

        /* Act */
        CartItemModel result = cartItemService.addMealToCart(null, mealId);

        /* Assert */
        assertNull(result);
        verify(cartItemRepository, never()).findByCartIdAndMealId(any(), any());
    }

    @Test
    void WhenCalled_AddMealToCart_WithNullCartId_ShouldReturnNull() {
        /* Arrange */
        CartModel cart = new CartModel(null, "No onions", new ArrayList<>());

        /* Act */
        CartItemModel result = cartItemService.addMealToCart(cart, 7L);

        /* Assert */
        assertNull(result);
        verify(cartItemRepository, never()).findByCartIdAndMealId(any(), any());
    }

    @Test
    void WhenCalled_RemoveMealFromCart_WithMultipleQuantity_ShouldDecreaseQuantity() {
        /* Arrange */
        CartModel cart = cart();
        CartItemModel cartItem = cartItem(cart, 2);
        cart.getCartItems().add(cartItem);
        when(cartItemRepository.findByCartIdAndMealId(1L, 7L)).thenReturn(Optional.of(cartItem));

        /* Act */
        boolean result = cartItemService.removeMealFromCart(cart, 7L);

        /* Assert */
        assertTrue(result);
        assertEquals(1, cartItem.getQuantity());
        assertEquals(1, cart.getCartItems().size());
        verify(cartItemRepository).save(cartItem);
        verify(cartItemRepository, never()).delete(any());
    }

    @Test
    void WhenCalled_RemoveMealFromCart_WithLastItem_ShouldDeleteCartItem() {
        /* Arrange */
        CartModel cart = cart();
        CartItemModel cartItem = cartItem(cart, 1);
        cart.getCartItems().add(cartItem);
        when(cartItemRepository.findByCartIdAndMealId(1L, 7L)).thenReturn(Optional.of(cartItem));

        /* Act */
        boolean result = cartItemService.removeMealFromCart(cart, 7L);

        /* Assert */
        assertTrue(result);
        assertTrue(cart.getCartItems().isEmpty());
        verify(cartItemRepository).delete(cartItem);
        verify(cartItemRepository, never()).save(any());
    }

    @Test
    void WhenCalled_RemoveMealFromCart_WithMissingItem_ShouldReturnFalse() {
        /* Arrange */
        CartModel cart = cart();
        when(cartItemRepository.findByCartIdAndMealId(1L, 7L)).thenReturn(Optional.empty());

        /* Act */
        boolean result = cartItemService.removeMealFromCart(cart, 7L);

        /* Assert */
        assertFalse(result);
        verify(cartItemRepository, never()).save(any());
        verify(cartItemRepository, never()).delete(any());
    }

    @Test
    void WhenCalled_RemoveMealFromCart_WithNullMealId_ShouldReturnFalse() {
        /* Arrange */
        CartModel cart = cart();

        /* Act */
        boolean result = cartItemService.removeMealFromCart(cart, null);

        /* Assert */
        assertFalse(result);
        verify(cartItemRepository, never()).findByCartIdAndMealId(any(), any());
    }

    @Test
    void WhenCalled_RemoveMealFromCart_WithNullCart_ShouldReturnFalse() {
        /* Arrange */
        Long mealId = 7L;

        /* Act */
        boolean result = cartItemService.removeMealFromCart(null, mealId);

        /* Assert */
        assertFalse(result);
        verify(cartItemRepository, never()).findByCartIdAndMealId(any(), any());
    }

    @Test
    void WhenCalled_RemoveMealFromCart_WithNullCartId_ShouldReturnFalse() {
        /* Arrange */
        CartModel cart = new CartModel(null, "No onions", new ArrayList<>());

        /* Act */
        boolean result = cartItemService.removeMealFromCart(cart, 7L);

        /* Assert */
        assertFalse(result);
        verify(cartItemRepository, never()).findByCartIdAndMealId(any(), any());
    }

    private CartModel cart() {
        return new CartModel(1L, "No onions", new ArrayList<>());
    }

    private MealModel meal() {
        return new MealModel(7L, "Pizza", "Margherita", new BigDecimal("32.00"), null);
    }

    private CartItemModel cartItem(CartModel cart, Integer quantity) {
        return new CartItemModel(3L, meal(), cart, quantity);
    }
}
