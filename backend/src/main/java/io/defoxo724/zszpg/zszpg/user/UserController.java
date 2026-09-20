package io.defoxo724.zszpg.zszpg.user;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
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
import io.defoxo724.zszpg.zszpg.user.dto.RegisterNewUserErrorResponse;
import io.defoxo724.zszpg.zszpg.user.dto.RegisterNewUserResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final UserAuthenticationService userAuthenticationService;
    private final int loginMinLength;
    private final int loginMaxLength;
    private final int passwordMinLength;
    private final int passwordMaxLength;

    public UserController(UserService userService, UserAuthenticationService userAuthenticationService,
            @Value("${app.auth.login.min_length}") int loginMinLength,
            @Value("${app.auth.login.max_length}") int loginMaxLength,
            @Value("${app.auth.password.min_length}") int passwordMinLength,
            @Value("${app.auth.password.max_length}") int passwordMaxLength) {
        this.userService = userService;
        this.userAuthenticationService = userAuthenticationService;
        this.loginMinLength = loginMinLength;
        this.loginMaxLength = loginMaxLength;
        this.passwordMinLength = passwordMinLength;
        this.passwordMaxLength = passwordMaxLength;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterNewUserRequest request) {
        Map<String, String> fieldErrors = validateRegistrationRequest(request);
        if (!fieldErrors.isEmpty()) {
            return ResponseEntity.badRequest().body(new RegisterNewUserErrorResponse(fieldErrors));
        }

        UserModel user = userService.register(request);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new RegisterNewUserErrorResponse(Map.of("login", "Ten login jest już zajęty.")));
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

    private Map<String, String> validateRegistrationRequest(RegisterNewUserRequest request) {
        Map<String, String> fieldErrors = new LinkedHashMap<>();
        String login = request.getLogin();
        String password = request.getPassword();

        if (login == null || login.isBlank()) {
            fieldErrors.put("login", "Login nie może być pusty.");
        } else if (login.length() < loginMinLength || login.length() > loginMaxLength) {
            fieldErrors.put("login",
                    "Login musi mieć od %d do %d znaków.".formatted(loginMinLength, loginMaxLength));
        }

        if (password == null || password.isBlank()) {
            fieldErrors.put("password", "Hasło nie może być puste.");
        } else if (password.length() < passwordMinLength || password.length() > passwordMaxLength) {
            fieldErrors.put("password",
                    "Hasło musi mieć od %d do %d znaków.".formatted(passwordMinLength, passwordMaxLength));
        }

        return fieldErrors;
    }
}
