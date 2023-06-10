package ge.temojudo.digitalauction.exceptions.mustbeunique;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;


@AllArgsConstructor
@Data
public class MustBeUniqueExceptionResponse {
    private Map<String, String> errors;
}
