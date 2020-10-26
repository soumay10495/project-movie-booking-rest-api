package project.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.persistence.EntityNotFoundException;

@RestControllerAdvice
public class ExceptionsHandler<T extends RuntimeException> {
    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleEntityException(T exception) {
        HttpStatus httpStatus;
        if (exception.getClass() == EntityNotFoundException.class)
            httpStatus = HttpStatus.NOT_FOUND;
        else
            httpStatus = HttpStatus.BAD_REQUEST;

        ErrorResponse response = new ErrorResponse(httpStatus.value(), exception.getMessage(),
                System.currentTimeMillis());
        return new ResponseEntity<>(response, httpStatus);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleGenericException(Exception exception) {
        ErrorResponse response = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                exception.getMessage(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
