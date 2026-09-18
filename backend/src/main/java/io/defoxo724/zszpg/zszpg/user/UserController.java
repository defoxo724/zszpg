package io.defoxo724.zszpg.zszpg.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.defoxo724.zszpg.zszpg.user.dto.GetCurrentSessionResponse;
import io.defoxo724.zszpg.zszpg.user.dto.LoginUserRequest;
import io.defoxo724.zszpg.zszpg.user.dto.LoginUserResponse;
import io.defoxo724.zszpg.zszpg.user.dto.LogoutUserResponse;
import io.defoxo724.zszpg.zszpg.user.dto.RegisterNewUserRequest;
import io.defoxo724.zszpg.zszpg.user.dto.RegisterNewUserResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final UserAuthenticationService userAuthenticationService;

    public UserController(UserService userService, UserAuthenticationService userAuthenticationService) {
        this.userService = userService;
        this.userAuthenticationService = userAuthenticationService;
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterNewUserResponse> register(@Valid @RequestBody RegisterNewUserRequest request) {
        UserModel user = userService.register(request);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(RegisterNewUserResponse.fromModel(user));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginUserResponse> login(@Valid @RequestBody LoginUserRequest request,
            HttpServletRequest httpRequest, HttpServletResponse httpResponse) {
        try {
            UserModel user = userAuthenticationService.login(request, httpRequest, httpResponse);
            return ResponseEntity.ok(LoginUserResponse.fromModel(user));
        } catch (AuthenticationException exception) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<LogoutUserResponse> logout(HttpServletRequest request) {
        userAuthenticationService.logout(request);
        return ResponseEntity.ok(new LogoutUserResponse("Logged out successfully."));
    }

    @GetMapping("/session")
    public ResponseEntity<GetCurrentSessionResponse> getCurrentSession(Authentication authentication) {
        UserModel user = (UserModel) authentication.getPrincipal();
        return ResponseEntity.ok(GetCurrentSessionResponse.fromModel(user));
    }
}
