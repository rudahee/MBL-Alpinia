package net.rudahee.movies.user.model.api;

import net.rudahee.movies.shared.exceptions.IError;

public enum UserError implements IError {

    USER_DOESNT_EXISTS("0003", "User doesnt exists"),
    BAD_LOGIN("0006", "Bad login");

    private final String code;
    private final String message;

    UserError(String code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public String getCode() {
        return code;
    }
}
