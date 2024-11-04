package net.rudahee.movies.user.model.api;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class LoginTokenDTO {
    private String token;

    private long expiresIn;
}
