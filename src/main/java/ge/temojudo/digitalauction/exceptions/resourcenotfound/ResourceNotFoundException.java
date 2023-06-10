package ge.temojudo.digitalauction.exceptions.resourcenotfound;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;


@AllArgsConstructor
@Getter
@Setter
public class ResourceNotFoundException extends RuntimeException {

    private Map<String, String> errors;

    public ResourceNotFoundException(String entityName, String fieldName, Object fieldValue) {
        this(Map.of(fieldName, String.format("[%s] not found with %s=[%s]", entityName, fieldName, fieldValue)));
    }

}
