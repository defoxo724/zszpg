package io.defoxo724.zszpg.zszpg.user;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.defoxo724.zszpg.zszpg.cart.CartService;
import io.defoxo724.zszpg.zszpg.user.dto.RegisterNewUserRequest;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final CartService cartService;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, CartService cartService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.cartService = cartService;
    }

    @Transactional
    public UserModel register(RegisterNewUserRequest request) {
        if (userRepository.existsByLogin(request.getLogin())) {
            return null;
        }
        UserModel user = new UserModel(null, request.getLogin(), passwordEncoder.encode(request.getPassword()),
                UserType.ORDERER);
        UserModel savedUser = userRepository.save(user);
        cartService.createCartForUser(savedUser);
        return savedUser;
    }

    public UserModel createAdmin(String login, String password) {
        if (userRepository.existsByType(UserType.ADMIN)) {
            return null;
        }
        return userRepository.save(new UserModel(null, login, passwordEncoder.encode(password), UserType.ADMIN));
    }

    @Override
    public UserDetails loadUserByUsername(String login) {
        return userRepository.findByLogin(login)
                .orElseThrow(() -> new UsernameNotFoundException("User does not exist."));
    }
}
