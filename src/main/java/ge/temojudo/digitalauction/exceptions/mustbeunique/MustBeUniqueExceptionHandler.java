package ge.temojudo.digitalauction.exceptions.mustbeunique;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@Slf4j
@RestControllerAdvice
public class MustBeUniqueExceptionHandler {

    @ExceptionHandler(MustBeUniqueException.class)
    public ResponseEntity<?> handleMustBeUniqueException(MustBeUniqueException e) {
        log.error("[handleMustBeUniqueException] value {}", e.getErrors());

        return new ResponseEntity<>(new MustBeUniqueExceptionResponse(e.getErrors()), HttpStatus.BAD_REQUEST);
    }

}
