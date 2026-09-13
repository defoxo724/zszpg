package io.defoxo724.zszpg.zszpg.cart;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.defoxo724.zszpg.zszpg.cart.dto.AddMealToCartResponse;
import io.defoxo724.zszpg.zszpg.cart.dto.CartCreateRequest;
import io.defoxo724.zszpg.zszpg.cart.dto.CartCreateResponse;
import io.defoxo724.zszpg.zszpg.cart.dto.CartGetAllResponse;
import io.defoxo724.zszpg.zszpg.cart.dto.CartGetByIdResponse;
import io.defoxo724.zszpg.zszpg.cart.dto.CartUpdateRequest;
import io.defoxo724.zszpg.zszpg.cart.dto.CartUpdateResponse;
import io.defoxo724.zszpg.zszpg.cart.dto.RemoveMealFromCartResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/carts")
@Tag(name = "Carts", description = "Manages shopping carts and the meals stored in them.")
public class CartController {
    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping
    @Operation(summary = "Create a cart", description = "Creates an empty shopping cart with an optional customer comment.")
    @ApiResponse(responseCode = "200", description = "The cart was created.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CartCreateResponse.class), examples = @ExampleObject(value = "{\"id\":1,\"comment\":\"No onions\",\"cartItems\":[]}")))
    public ResponseEntity<CartCreateResponse> createCart(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "The data of the cart to create.", required = true, content = @Content(schema = @Schema(implementation = CartCreateRequest.class), examples = @ExampleObject(value = "{\"comment\":\"No onions\"}"))) @RequestBody CartCreateRequest cart) {
        CartModel createdCart = cartService.createCart(cart.toModel());
        return ResponseEntity.ok(CartCreateResponse.fromModel(createdCart));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a cart", description = "Returns a cart and all of its meal items.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "The cart was found.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CartGetByIdResponse.class), examples = @ExampleObject(value = "{\"id\":1,\"comment\":\"No onions\",\"cartItems\":[{\"id\":3,\"mealId\":7,\"quantity\":2}]}"))),
            @ApiResponse(responseCode = "404", description = "A cart with the specified identifier does not exist.", content = @Content)
    })
    public ResponseEntity<CartGetByIdResponse> getCartById(
            @Parameter(description = "The cart identifier.", example = "1", required = true) @PathVariable Long id) {
        CartModel cart = cartService.getCartById(id);
        if (cart == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(CartGetByIdResponse.fromModel(cart));
    }

    @GetMapping
    @Operation(summary = "Get all carts", description = "Returns all carts together with their meal items. When no carts exist, the carts field contains an empty list.")
    @ApiResponse(responseCode = "200", description = "The cart list was returned.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CartGetAllResponse.class), examples = @ExampleObject(value = "{\"carts\":[{\"id\":1,\"comment\":\"No onions\",\"cartItems\":[{\"id\":3,\"mealId\":7,\"quantity\":2}]}]}")))
    public ResponseEntity<CartGetAllResponse> getAllCarts() {
        return ResponseEntity.ok(CartGetAllResponse.fromModelList(cartService.getAllCarts()));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a cart", description = "Replaces the comment of the cart with the specified identifier without changing its meal items.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "The cart was updated.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CartUpdateResponse.class), examples = @ExampleObject(value = "{\"id\":1,\"comment\":\"Extra spicy\",\"cartItems\":[{\"id\":3,\"mealId\":7,\"quantity\":2}]}"))),
            @ApiResponse(responseCode = "404", description = "A cart with the specified identifier does not exist.", content = @Content)
    })
    public ResponseEntity<CartUpdateResponse> updateCartById(
            @Parameter(description = "The identifier of the cart to update.", example = "1", required = true) @PathVariable Long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "The replacement cart comment.", required = true, content = @Content(schema = @Schema(implementation = CartUpdateRequest.class), examples = @ExampleObject(value = "{\"comment\":\"Extra spicy\"}"))) @RequestBody CartUpdateRequest cart) {
        CartModel updatedCart = cartService.updateCartById(id, cart.toModel());
        if (updatedCart == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(CartUpdateResponse.fromModel(updatedCart));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a cart", description = "Deletes the specified cart and all of its meal items. The operation returns no response body.")
    @ApiResponse(responseCode = "204", description = "The delete request was completed.", content = @Content)
    public ResponseEntity<Void> deleteCart(
            @Parameter(description = "The identifier of the cart to delete.", example = "1", required = true) @PathVariable Long id) {
        cartService.deleteCartById(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{cartId}/meals/{mealId}")
    @Operation(summary = "Add a meal to a cart", description = "Creates a cart item with quantity 1, or increments its quantity when the meal is already present in the cart.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "The meal was added to the cart.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AddMealToCartResponse.class), examples = @ExampleObject(value = "{\"id\":1,\"comment\":\"No onions\",\"cartItems\":[{\"id\":3,\"mealId\":7,\"quantity\":2}]}"))),
            @ApiResponse(responseCode = "404", description = "The cart or meal does not exist.", content = @Content)
    })
    public ResponseEntity<AddMealToCartResponse> addMealToCart(
            @Parameter(description = "The cart identifier.", example = "1", required = true) @PathVariable Long cartId,
            @Parameter(description = "The identifier of the meal to add.", example = "7", required = true) @PathVariable Long mealId) {
        CartModel cart = cartService.addMealToCart(cartId, mealId);
        if (cart == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(AddMealToCartResponse.fromModel(cart));
    }

    @DeleteMapping("/{cartId}/meals/{mealId}")
    @Operation(summary = "Remove a meal from a cart", description = "Decrements the cart item quantity, or removes the item completely when its quantity is 1.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "One unit of the meal was removed from the cart.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = RemoveMealFromCartResponse.class), examples = @ExampleObject(value = "{\"id\":1,\"comment\":\"No onions\",\"cartItems\":[{\"id\":3,\"mealId\":7,\"quantity\":1}]}"))),
            @ApiResponse(responseCode = "404", description = "The cart does not exist or does not contain the specified meal.", content = @Content)
    })
    public ResponseEntity<RemoveMealFromCartResponse> removeMealFromCart(
            @Parameter(description = "The cart identifier.", example = "1", required = true) @PathVariable Long cartId,
            @Parameter(description = "The identifier of the meal to remove.", example = "7", required = true) @PathVariable Long mealId) {
        CartModel cart = cartService.removeMealFromCart(cartId, mealId);
        if (cart == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(RemoveMealFromCartResponse.fromModel(cart));
    }
}
