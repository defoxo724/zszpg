package io.defoxo724.zszpg.zszpg.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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
    @NotBlank
    @Size(min = 3, max = 50)
    private String login;
    @NotBlank
    @Size(min = 8, max = 72)
    private String password;
}
