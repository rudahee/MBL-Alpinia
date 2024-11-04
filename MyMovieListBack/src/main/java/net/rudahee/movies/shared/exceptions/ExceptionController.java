package net.rudahee.movies.shared.exceptions;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionController extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = { Exception.class })
    protected ResponseEntity<Object> handleNotControlledException(Exception ex) {
        ex.printStackTrace();
        return ResponseEntity.internalServerError().body(CommonErrorType.INDETERMINATE_ERROR);
    }

    @ExceptionHandler(value = { APIException.class })
    protected ResponseEntity<Object> handleCustomException(APIException ex) {
        ex.printStackTrace();
        return ResponseEntity.badRequest().body(ex.getCode());
    }
}
