package io.defoxo724.zszpg.zszpg.user;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import io.defoxo724.zszpg.zszpg.user.dto.RegisterNewUserRequest;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserModel register(RegisterNewUserRequest request) {
        if (userRepository.existsByLogin(request.getLogin())) {
            return null;
        }
        UserModel user = new UserModel(null, request.getLogin(), passwordEncoder.encode(request.getPassword()),
                UserType.ORDERER);
        return userRepository.save(user);
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
