package net.rudahee.movies.group.model.api;

import net.rudahee.movies.shared.exceptions.IError;

public enum GroupError implements IError {
    GROUP_DOESNT_EXISTS("0018", "Group doesnt exists");


    private final String code;
    private final String message;

    GroupError(String code, String message) {
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
