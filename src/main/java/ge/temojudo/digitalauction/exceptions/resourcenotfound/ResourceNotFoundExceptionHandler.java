package ge.temojudo.digitalauction.exceptions.resourcenotfound;

import ge.temojudo.digitalauction.exceptions.mustbeunique.MustBeUniqueException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@Slf4j
@RestControllerAdvice
public class ResourceNotFoundExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleResourceNotFoundException(MustBeUniqueException e) {
        log.error("[handleResourceNotFoundException] value {}", e.getErrors());

        return new ResponseEntity<>(new ResourceNotFoundExceptionResponse(e.getErrors()), HttpStatus.NOT_FOUND);
    }

}
