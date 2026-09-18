package io.defoxo724.zszpg.zszpg.cart_item;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItemModel, Long> {
    Optional<CartItemModel> findByCartIdAndMealId(Long cartId, Long mealId);
}
