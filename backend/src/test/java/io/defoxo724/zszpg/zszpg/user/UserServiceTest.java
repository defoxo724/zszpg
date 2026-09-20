package io.defoxo724.zszpg.zszpg.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import io.defoxo724.zszpg.zszpg.cart.CartService;
import io.defoxo724.zszpg.zszpg.user.dto.RegisterNewUserRequest;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private CartService cartService;
    @InjectMocks
    private UserService userService;

    @Test
    void WhenCalled_Register_WithNewLogin_ShouldCreateUserAndCart() {
        /* Arrange */
        RegisterNewUserRequest request = new RegisterNewUserRequest("customer", "password123", "password123");
        UserModel savedUser = new UserModel(1L, "customer", "encoded-password", UserType.ORDERER);
        when(userRepository.existsByLogin("customer")).thenReturn(false);
        when(passwordEncoder.encode("password123")).thenReturn("encoded-password");
        when(userRepository.save(any(UserModel.class))).thenReturn(savedUser);

        /* Act */
        UserModel result = userService.register(request);

        /* Assert */
        assertSame(savedUser, result);
        verify(cartService).createCartForUser(savedUser);
    }

    @Test
    void WhenCalled_Register_WithExistingLogin_ShouldReturnNullAndNotCreateCart() {
        /* Arrange */
        RegisterNewUserRequest request = new RegisterNewUserRequest("customer", "password123", "password123");
        when(userRepository.existsByLogin("customer")).thenReturn(true);

        /* Act */
        UserModel result = userService.register(request);

        /* Assert */
        assertNull(result);
        verify(userRepository, never()).save(org.mockito.ArgumentMatchers.any());
        verify(cartService, never()).createCartForUser(org.mockito.ArgumentMatchers.any());
    }

    @Test
    void WhenCalled_CreateAdmin_WithoutExistingAdmin_ShouldCreateAdminWithoutCart() {
        /* Arrange */
        UserModel savedAdmin = new UserModel(1L, "admin", "encoded-password", UserType.ADMIN);
        when(userRepository.existsByType(UserType.ADMIN)).thenReturn(false);
        when(passwordEncoder.encode("password123")).thenReturn("encoded-password");
        when(userRepository.save(any(UserModel.class))).thenReturn(savedAdmin);

        /* Act */
        UserModel result = userService.createAdmin("admin", "password123");

        /* Assert */
        assertSame(savedAdmin, result);
        verify(cartService, never()).createCartForUser(org.mockito.ArgumentMatchers.any());
    }

    @Test
    void WhenCalled_CreateAdmin_WithExistingAdmin_ShouldReturnNull() {
        /* Arrange */
        when(userRepository.existsByType(UserType.ADMIN)).thenReturn(true);

        /* Act */
        UserModel result = userService.createAdmin("admin", "password123");

        /* Assert */
        assertNull(result);
        verify(userRepository, never()).save(org.mockito.ArgumentMatchers.any());
    }

    @Test
    void WhenCalled_LoadUserByUsername_WithExistingUser_ShouldReturnUser() {
        /* Arrange */
        UserModel user = new UserModel(1L, "customer", "encoded-password", UserType.ORDERER);
        when(userRepository.findByLogin("customer")).thenReturn(Optional.of(user));

        /* Act */
        UserDetails result = userService.loadUserByUsername("customer");

        /* Assert */
        assertSame(user, result);
    }

    @Test
    void WhenCalled_LoadUserByUsername_WithMissingUser_ShouldThrowException() {
        /* Arrange */
        when(userRepository.findByLogin("missing")).thenReturn(Optional.empty());

        /* Act */
        UsernameNotFoundException exception = assertThrows(UsernameNotFoundException.class,
                () -> userService.loadUserByUsername("missing"));

        /* Assert */
        assertEquals("User does not exist.", exception.getMessage());
    }
}
