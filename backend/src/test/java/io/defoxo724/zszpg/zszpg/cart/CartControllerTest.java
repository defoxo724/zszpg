package io.defoxo724.zszpg.zszpg.cart;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import io.defoxo724.zszpg.zszpg.cart.dto.AddMealToCartResponse;
import io.defoxo724.zszpg.zszpg.cart.dto.CartGetAllResponse;
import io.defoxo724.zszpg.zszpg.cart.dto.CartGetByIdResponse;
import io.defoxo724.zszpg.zszpg.cart.dto.CartUpdateRequest;
import io.defoxo724.zszpg.zszpg.cart.dto.CartUpdateResponse;
import io.defoxo724.zszpg.zszpg.cart.dto.RemoveMealFromCartResponse;

@ExtendWith(MockitoExtension.class)
class CartControllerTest {
    @Mock
    private CartService cartService;
    @InjectMocks
    private CartController cartController;

    @Test
    void WhenCalled_GetCartById_WithExistingCart_ShouldReturnCart() {
        /* Arrange */
        CartModel cart = cart(1L, "No onions");
        when(cartService.getCartById(1L)).thenReturn(cart);

        /* Act */
        ResponseEntity<CartGetByIdResponse> result = cartController.getCartById(1L);

        /* Assert */
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(CartGetByIdResponse.fromModel(cart), result.getBody());
    }

    @Test
    void WhenCalled_GetCartById_WithMissingCart_ShouldReturnNotFound() {
        /* Arrange */
        when(cartService.getCartById(1L)).thenReturn(null);

        /* Act */
        ResponseEntity<CartGetByIdResponse> result = cartController.getCartById(1L);

        /* Assert */
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
        assertNull(result.getBody());
    }

    @Test
    void WhenCalled_GetCurrentUserCart_WithExistingCart_ShouldReturnCart() {
        /* Arrange */
        CartModel cart = cart(1L, "No onions");
        when(cartService.getCurrentUserCart()).thenReturn(cart);

        /* Act */
        ResponseEntity<CartGetByIdResponse> result = cartController.getCurrentUserCart();

        /* Assert */
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(CartGetByIdResponse.fromModel(cart), result.getBody());
    }

    @Test
    void WhenCalled_GetCurrentUserCart_WithMissingCart_ShouldReturnNotFound() {
        /* Arrange */
        when(cartService.getCurrentUserCart()).thenReturn(null);

        /* Act */
        ResponseEntity<CartGetByIdResponse> result = cartController.getCurrentUserCart();

        /* Assert */
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
        assertNull(result.getBody());
    }

    @Test
    void WhenCalled_GetAllCarts_ShouldReturnAllCarts() {
        /* Arrange */
        List<CartModel> carts = List.of(cart(1L, "No onions"));
        when(cartService.getAllCarts()).thenReturn(carts);

        /* Act */
        ResponseEntity<CartGetAllResponse> result = cartController.getAllCarts();

        /* Assert */
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(CartGetAllResponse.fromModelList(carts), result.getBody());
    }

    @Test
    void WhenCalled_UpdateCartById_WithExistingCart_ShouldReturnUpdatedCart() {
        /* Arrange */
        CartUpdateRequest request = new CartUpdateRequest("Extra spicy");
        CartModel updatedCart = cart(1L, "Extra spicy");
        when(cartService.updateCartById(org.mockito.ArgumentMatchers.eq(1L), any(CartModel.class)))
                .thenReturn(updatedCart);

        /* Act */
        ResponseEntity<CartUpdateResponse> result = cartController.updateCartById(1L, request);

        /* Assert */
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(CartUpdateResponse.fromModel(updatedCart), result.getBody());
    }

    @Test
    void WhenCalled_UpdateCartById_WithMissingCart_ShouldReturnNotFound() {
        /* Arrange */
        CartUpdateRequest request = new CartUpdateRequest("Extra spicy");
        when(cartService.updateCartById(org.mockito.ArgumentMatchers.eq(1L), any(CartModel.class))).thenReturn(null);

        /* Act */
        ResponseEntity<CartUpdateResponse> result = cartController.updateCartById(1L, request);

        /* Assert */
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
        assertNull(result.getBody());
    }

    @Test
    void WhenCalled_AddMealToCart_WithExistingCartAndMeal_ShouldReturnCart() {
        /* Arrange */
        CartModel cart = cart(1L, "No onions");
        when(cartService.addMealToCart(1L, 7L)).thenReturn(cart);

        /* Act */
        ResponseEntity<AddMealToCartResponse> result = cartController.addMealToCart(1L, 7L);

        /* Assert */
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(AddMealToCartResponse.fromModel(cart), result.getBody());
    }

    @Test
    void WhenCalled_AddMealToCart_WithMissingCartOrMeal_ShouldReturnNotFound() {
        /* Arrange */
        when(cartService.addMealToCart(1L, 7L)).thenReturn(null);

        /* Act */
        ResponseEntity<AddMealToCartResponse> result = cartController.addMealToCart(1L, 7L);

        /* Assert */
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
        assertNull(result.getBody());
    }

    @Test
    void WhenCalled_RemoveMealFromCart_WithExistingItem_ShouldReturnCart() {
        /* Arrange */
        CartModel cart = cart(1L, "No onions");
        when(cartService.removeMealFromCart(1L, 7L)).thenReturn(cart);

        /* Act */
        ResponseEntity<RemoveMealFromCartResponse> result = cartController.removeMealFromCart(1L, 7L);

        /* Assert */
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(RemoveMealFromCartResponse.fromModel(cart), result.getBody());
    }

    @Test
    void WhenCalled_RemoveMealFromCart_WithMissingCartOrItem_ShouldReturnNotFound() {
        /* Arrange */
        when(cartService.removeMealFromCart(1L, 7L)).thenReturn(null);

        /* Act */
        ResponseEntity<RemoveMealFromCartResponse> result = cartController.removeMealFromCart(1L, 7L);

        /* Assert */
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
        assertNull(result.getBody());
    }

    private CartModel cart(Long id, String comment) {
        return new CartModel(id, comment, new ArrayList<>());
    }
}
