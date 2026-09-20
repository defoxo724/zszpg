package io.defoxo724.zszpg.zszpg.user.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class RegisterNewUserRequest {
    private String login;
    private String password;
    private String passwordConfirmation;
}
