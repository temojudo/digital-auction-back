package ge.temojudo.digitalauction.model.users.registeruser;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Data
public class RegisterUserRequest {

    @NotEmpty
    private String firstname;
    @NotEmpty
    private String lastname;
    @NotEmpty
    private String username;
    @NotEmpty
    private String password;
    @NotNull
    @Size(min = 11, max = 11)
    private String personalNumber;

}
