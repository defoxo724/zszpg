package io.defoxo724.zszpg.zszpg.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;

import io.defoxo724.zszpg.zszpg.user.dto.GetCurrentSessionResponse;
import io.defoxo724.zszpg.zszpg.user.dto.LoginUserRequest;
import io.defoxo724.zszpg.zszpg.user.dto.LoginUserResponse;
import io.defoxo724.zszpg.zszpg.user.dto.LogoutUserResponse;
import io.defoxo724.zszpg.zszpg.user.dto.RegisterNewUserErrorResponse;
import io.defoxo724.zszpg.zszpg.user.dto.RegisterNewUserRequest;
import io.defoxo724.zszpg.zszpg.user.dto.RegisterNewUserResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {
    @Mock
    private UserService userService;
    @Mock
    private UserAuthenticationService userAuthenticationService;
    @Mock
    private HttpServletRequest httpRequest;
    @Mock
    private HttpServletResponse httpResponse;
    @Mock
    private Authentication authentication;

    private UserController userController;

    @BeforeEach
    void setUp() {
        userController = new UserController(userService, userAuthenticationService, 3, 50, 8, 72);
    }

    @Test
    void WhenCalled_Register_WithValidRequest_ShouldReturnCreatedUser() {
        /* Arrange */
        RegisterNewUserRequest request = new RegisterNewUserRequest("customer", "password123", "password123");
        UserModel user = new UserModel(1L, "customer", "encoded-password", UserType.ORDERER);
        when(userService.register(request)).thenReturn(user);

        /* Act */
        ResponseEntity<?> result = userController.register(request);

        /* Assert */
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertEquals(RegisterNewUserResponse.fromModel(user), result.getBody());
    }

    @Test
    void WhenCalled_Register_WithInvalidLengths_ShouldReturnFieldErrors() {
        /* Arrange */
        RegisterNewUserRequest request = new RegisterNewUserRequest("ab", "short", "short");
        RegisterNewUserErrorResponse expectedBody = new RegisterNewUserErrorResponse(Map.of(
                "login", "Login musi mieć od 3 do 50 znaków.",
                "password", "Hasło musi mieć od 8 do 72 znaków."));

        /* Act */
        ResponseEntity<?> result = userController.register(request);

        /* Assert */
        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
        assertEquals(expectedBody, result.getBody());
        verifyNoInteractions(userService);
    }

    @Test
    void WhenCalled_Register_WithBlankFields_ShouldReturnFieldErrors() {
        /* Arrange */
        RegisterNewUserRequest request = new RegisterNewUserRequest(" ", null, null);
        RegisterNewUserErrorResponse expectedBody = new RegisterNewUserErrorResponse(Map.of(
                "login", "Login nie może być pusty.",
                "password", "Hasło nie może być puste.",
                "passwordConfirmation", "Powtórzenie hasła nie może być puste."));

        /* Act */
        ResponseEntity<?> result = userController.register(request);

        /* Assert */
        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
        assertEquals(expectedBody, result.getBody());
        verifyNoInteractions(userService);
    }

    @Test
    void WhenCalled_Register_WithDifferentPasswords_ShouldReturnConfirmationError() {
        /* Arrange */
        RegisterNewUserRequest request = new RegisterNewUserRequest("customer", "password123", "different123");

        /* Act */
        ResponseEntity<?> result = userController.register(request);

        /* Assert */
        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
        assertEquals(new RegisterNewUserErrorResponse(Map.of("passwordConfirmation", "Hasła nie są takie same.")),
                result.getBody());
        verifyNoInteractions(userService);
    }

    @Test
    void WhenCalled_Register_WithExistingLogin_ShouldReturnLoginError() {
        /* Arrange */
        RegisterNewUserRequest request = new RegisterNewUserRequest("customer", "password123", "password123");
        when(userService.register(request)).thenReturn(null);

        /* Act */
        ResponseEntity<?> result = userController.register(request);

        /* Assert */
        assertEquals(HttpStatus.CONFLICT, result.getStatusCode());
        assertEquals(new RegisterNewUserErrorResponse(Map.of("login", "Ten login jest już zajęty.")),
                result.getBody());
    }

    @Test
    void WhenCalled_Login_WithValidCredentials_ShouldReturnLoggedInUser() {
        /* Arrange */
        LoginUserRequest request = new LoginUserRequest("customer", "password123");
        UserModel user = new UserModel(1L, "customer", "encoded-password", UserType.ORDERER);
        when(userAuthenticationService.login(request, httpRequest, httpResponse)).thenReturn(user);

        /* Act */
        ResponseEntity<LoginUserResponse> result = userController.login(request, httpRequest, httpResponse);

        /* Assert */
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(LoginUserResponse.fromModel(user), result.getBody());
    }

    @Test
    void WhenCalled_Login_WithInvalidCredentials_ShouldReturnUnauthorized() {
        /* Arrange */
        LoginUserRequest request = new LoginUserRequest("customer", "wrong-password");
        doThrow(new BadCredentialsException("Invalid credentials"))
                .when(userAuthenticationService).login(request, httpRequest, httpResponse);

        /* Act */
        ResponseEntity<LoginUserResponse> result = userController.login(request, httpRequest, httpResponse);

        /* Assert */
        assertEquals(HttpStatus.UNAUTHORIZED, result.getStatusCode());
        assertEquals(null, result.getBody());
    }

    @Test
    void WhenCalled_Logout_ShouldClearSessionAndReturnConfirmation() {
        /* Arrange */
        LogoutUserResponse expectedBody = new LogoutUserResponse("Logged out successfully.");

        /* Act */
        ResponseEntity<LogoutUserResponse> result = userController.logout(httpRequest);

        /* Assert */
        verify(userAuthenticationService).logout(httpRequest);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(expectedBody, result.getBody());
    }

    @Test
    void WhenCalled_GetCurrentSession_ShouldReturnAuthenticatedUser() {
        /* Arrange */
        UserModel user = new UserModel(1L, "customer", "encoded-password", UserType.ORDERER);
        when(authentication.getPrincipal()).thenReturn(user);

        /* Act */
        ResponseEntity<GetCurrentSessionResponse> result = userController.getCurrentSession(authentication);

        /* Assert */
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(GetCurrentSessionResponse.fromModel(user), result.getBody());
    }
}
