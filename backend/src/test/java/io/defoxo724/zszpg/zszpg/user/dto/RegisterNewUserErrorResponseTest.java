package io.defoxo724.zszpg.zszpg.user.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Map;

import org.junit.jupiter.api.Test;

class RegisterNewUserErrorResponseTest {
    @Test
    void WhenCalled_Constructor_ShouldSetFieldErrors() {
        /* Arrange */
        Map<String, String> fieldErrors = Map.of("login", "Invalid login");

        /* Act */
        RegisterNewUserErrorResponse response = new RegisterNewUserErrorResponse(fieldErrors);

        /* Assert */
        assertEquals(fieldErrors, response.getFieldErrors());
    }
}
