package io.defoxo724.zszpg.zszpg.cart;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<CartModel, Long> {
    Optional<CartModel> findByOwnerId(Long ownerId);
}
