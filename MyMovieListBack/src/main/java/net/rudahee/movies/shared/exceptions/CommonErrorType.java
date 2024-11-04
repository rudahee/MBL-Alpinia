package net.rudahee.movies.shared.exceptions;

public enum CommonErrorType implements IError {

    NO_ERROR("0", "OK"),
    INDETERMINATE_ERROR("-1", "Unknown error. Contact an administrator"),
    PARAMETER_IS_MISSING("0001","Some required parameter is missing"),
    BODY_IS_MISSING("0002","Body is missing");


    private final String code;
    private final String message;

    private CommonErrorType(String code, String message) {
        this.message = message;
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public String getCode() {
        return code;
    }
}
