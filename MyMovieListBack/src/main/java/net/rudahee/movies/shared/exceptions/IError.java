package net.rudahee.movies.shared.exceptions;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public interface IError {

    String getMessage();
    String getCode();
}
