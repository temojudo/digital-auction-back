package ge.temojudo.digitalauction.exceptions.invalidargument;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;


@AllArgsConstructor
@Getter
@Setter
public class InvalidArgumentException extends RuntimeException {

    private Map<String, String> errors;

    public InvalidArgumentException(String fieldName, String errorDesc) {
        this(Map.of(fieldName, errorDesc));
    }

}
