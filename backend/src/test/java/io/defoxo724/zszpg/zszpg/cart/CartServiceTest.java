package io.defoxo724.zszpg.zszpg.cart;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import io.defoxo724.zszpg.zszpg.cart_item.CartItemModel;
import io.defoxo724.zszpg.zszpg.cart_item.CartItemService;

@ExtendWith(MockitoExtension.class)
class CartServiceTest {
    @Mock
    private CartRepository cartRepository;
    @Mock
    private CartItemService cartItemService;
    @InjectMocks
    private CartService cartService;

    @Test
    void WhenCalled_CreateCart_ShouldReturnSavedCart() {
        /* Arrange */
        CartModel cart = cart(null, "No onions");
        CartModel savedCart = cart(1L, "No onions");
        when(cartRepository.save(cart)).thenReturn(savedCart);

        /* Act */
        CartModel result = cartService.createCart(cart);

        /* Assert */
        assertSame(savedCart, result);
        verify(cartRepository).save(cart);
    }

    @Test
    void WhenCalled_GetCartById_WithExistingCart_ShouldReturnCart() {
        /* Arrange */
        CartModel cart = cart(1L, "No onions");
        when(cartRepository.findById(1L)).thenReturn(Optional.of(cart));

        /* Act */
        CartModel result = cartService.getCartById(1L);

        /* Assert */
        assertSame(cart, result);
    }

    @Test
    void WhenCalled_GetCartById_WithMissingCart_ShouldReturnNull() {
        /* Arrange */
        when(cartRepository.findById(1L)).thenReturn(Optional.empty());

        /* Act */
        CartModel result = cartService.getCartById(1L);

        /* Assert */
        assertNull(result);
    }

    @Test
    void WhenCalled_GetAllCarts_ShouldReturnRepositoryResult() {
        /* Arrange */
        List<CartModel> carts = List.of(cart(1L, "No onions"));
        when(cartRepository.findAll()).thenReturn(carts);

        /* Act */
        List<CartModel> result = cartService.getAllCarts();

        /* Assert */
        assertSame(carts, result);
    }

    @Test
    void WhenCalled_UpdateCartById_WithExistingCart_ShouldUpdateComment() {
        /* Arrange */
        CartModel existingCart = cart(1L, "Old comment");
        CartModel changes = cart(null, "New comment");
        when(cartRepository.findById(1L)).thenReturn(Optional.of(existingCart));
        when(cartRepository.save(existingCart)).thenReturn(existingCart);

        /* Act */
        CartModel result = cartService.updateCartById(1L, changes);

        /* Assert */
        assertSame(existingCart, result);
        assertEquals("New comment", existingCart.getComment());
        verify(cartRepository).save(existingCart);
    }

    @Test
    void WhenCalled_UpdateCartById_WithMissingCart_ShouldReturnNull() {
        /* Arrange */
        CartModel changes = cart(null, "New comment");
        when(cartRepository.findById(1L)).thenReturn(Optional.empty());

        /* Act */
        CartModel result = cartService.updateCartById(1L, changes);

        /* Assert */
        assertNull(result);
        verify(cartRepository, never()).save(any());
    }

    @Test
    void WhenCalled_DeleteCartById_WithExistingCart_ShouldDeleteCart() {
        /* Arrange */
        when(cartRepository.findById(1L)).thenReturn(Optional.of(cart(1L, "No onions")));

        /* Act */
        cartService.deleteCartById(1L);

        /* Assert */
        verify(cartRepository).deleteById(1L);
    }

    @Test
    void WhenCalled_DeleteCartById_WithMissingCart_ShouldNotDeleteCart() {
        /* Arrange */
        when(cartRepository.findById(1L)).thenReturn(Optional.empty());

        /* Act */
        cartService.deleteCartById(1L);

        /* Assert */
        verify(cartRepository, never()).deleteById(1L);
    }

    @Test
    void WhenCalled_AddMealToCart_WithExistingCart_ShouldReturnCart() {
        /* Arrange */
        CartModel cart = cart(1L, "No onions");
        CartItemModel cartItem = new CartItemModel(3L, null, cart, 1);
        when(cartRepository.findById(1L)).thenReturn(Optional.of(cart));
        when(cartItemService.addMealToCart(cart, 7L)).thenReturn(cartItem);

        /* Act */
        CartModel result = cartService.addMealToCart(1L, 7L);

        /* Assert */
        assertSame(cart, result);
    }

    @Test
    void WhenCalled_AddMealToCart_WithMissingCart_ShouldReturnNull() {
        /* Arrange */
        when(cartRepository.findById(1L)).thenReturn(Optional.empty());

        /* Act */
        CartModel result = cartService.addMealToCart(1L, 7L);

        /* Assert */
        assertNull(result);
        verify(cartItemService, never()).addMealToCart(any(), any());
    }

    @Test
    void WhenCalled_AddMealToCart_WithMissingMeal_ShouldReturnNull() {
        /* Arrange */
        CartModel cart = cart(1L, "No onions");
        when(cartRepository.findById(1L)).thenReturn(Optional.of(cart));
        when(cartItemService.addMealToCart(cart, 7L)).thenReturn(null);

        /* Act */
        CartModel result = cartService.addMealToCart(1L, 7L);

        /* Assert */
        assertNull(result);
    }

    @Test
    void WhenCalled_RemoveMealFromCart_WithExistingItem_ShouldReturnCart() {
        /* Arrange */
        CartModel cart = cart(1L, "No onions");
        when(cartRepository.findById(1L)).thenReturn(Optional.of(cart));
        when(cartItemService.removeMealFromCart(cart, 7L)).thenReturn(true);

        /* Act */
        CartModel result = cartService.removeMealFromCart(1L, 7L);

        /* Assert */
        assertSame(cart, result);
    }

    @Test
    void WhenCalled_RemoveMealFromCart_WithMissingCart_ShouldReturnNull() {
        /* Arrange */
        when(cartRepository.findById(1L)).thenReturn(Optional.empty());

        /* Act */
        CartModel result = cartService.removeMealFromCart(1L, 7L);

        /* Assert */
        assertNull(result);
        verify(cartItemService, never()).removeMealFromCart(any(), any());
    }

    @Test
    void WhenCalled_RemoveMealFromCart_WithMissingItem_ShouldReturnNull() {
        /* Arrange */
        CartModel cart = cart(1L, "No onions");
        when(cartRepository.findById(1L)).thenReturn(Optional.of(cart));
        when(cartItemService.removeMealFromCart(cart, 7L)).thenReturn(false);

        /* Act */
        CartModel result = cartService.removeMealFromCart(1L, 7L);

        /* Assert */
        assertNull(result);
    }

    private CartModel cart(Long id, String comment) {
        return new CartModel(id, comment, new ArrayList<>());
    }
}
