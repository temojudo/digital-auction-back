package ge.temojudo.digitalauction.exceptions.invalidargument;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@Slf4j
@RestControllerAdvice
public class InvalidArgumentExceptionHandler {

    @ExceptionHandler(InvalidArgumentException.class)
    public ResponseEntity<?> handleInvalidArgumentException(InvalidArgumentException e) {
        log.error("[handleInvalidArgumentException] value {}", e.getErrors());

        return new ResponseEntity<>(new InvalidArgumentExceptionResponse(e.getErrors()), HttpStatus.BAD_REQUEST);
    }

}
