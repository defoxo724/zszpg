package io.defoxo724.zszpg.zszpg.user.dto;

import java.util.Map;

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
public class RegisterNewUserErrorResponse {
    private Map<String, String> fieldErrors;
}
