package ge.temojudo.digitalauction.exceptions.invalidargument;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;


@AllArgsConstructor
@Data
public class InvalidArgumentExceptionResponse {
    private Map<String, String> errors;
}
