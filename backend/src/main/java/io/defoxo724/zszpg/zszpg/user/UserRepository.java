package io.defoxo724.zszpg.zszpg.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserModel, Long> {
    Optional<UserModel> findByLogin(String login);

    boolean existsByLogin(String login);

    boolean existsByType(UserType type);
}
