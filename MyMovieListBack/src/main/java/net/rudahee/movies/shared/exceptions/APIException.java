package net.rudahee.movies.shared.exceptions;


public class APIException extends Exception {
    private final IError apiError;

    public APIException(IError apiError) {
        super();
        this.apiError = apiError;
    }

    public IError getCode() {
        return this.apiError;
    }
}
