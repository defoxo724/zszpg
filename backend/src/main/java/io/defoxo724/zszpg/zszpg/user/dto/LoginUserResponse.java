package io.defoxo724.zszpg.zszpg.user.dto;

import io.defoxo724.zszpg.zszpg.user.UserModel;
import io.defoxo724.zszpg.zszpg.user.UserType;
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
public class LoginUserResponse {
    private Long id;
    private String login;
    private UserType type;

    public static LoginUserResponse fromModel(UserModel user) {
        return new LoginUserResponse(user.getId(), user.getLogin(), user.getType());
    }
}
