package ge.temojudo.digitalauction.exceptions.mustbeunique;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;


@AllArgsConstructor
@Getter
@Setter
public class MustBeUniqueException extends RuntimeException {

    private Map<String, String> errors;

    public MustBeUniqueException(String fieldName, String fieldValue) {
        this(Map.of(fieldName, String.format("[%s] already exists", fieldValue)));
    }

}
