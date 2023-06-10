package ge.temojudo.digitalauction.exceptions.resourcenotfound;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;


@AllArgsConstructor
@Data
public class ResourceNotFoundExceptionResponse {
    private Map<String, String> errors;
}
