package ge.temojudo.digitalauction.service.users;

import ge.temojudo.digitalauction.entity.users.UserEntity;
import ge.temojudo.digitalauction.exceptions.mustbeunique.MustBeUniqueException;
import ge.temojudo.digitalauction.model.users.registeruser.RegisterUserRequest;
import ge.temojudo.digitalauction.model.users.registeruser.RegisterUserResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class RegistrationService {

    private final UsersService usersService;
    private final PasswordEncoder passwordEncoder;

    public RegisterUserResponse register(RegisterUserRequest request) {
        String username = request.getUsername();
        String personalNumber = request.getPersonalNumber();

        if (usersService.existsByUsernameOrPersonalNumber(username, personalNumber)) {
            throw new MustBeUniqueException(
                    "username|personalNumber",
                    String.format("[%s]|[%s]", username, personalNumber));
        }

        UserEntity userEntity = usersService.save(new UserEntity(
                request.getFirstname(),
                request.getLastname(),
                username,
                passwordEncoder.encode(request.getPassword()),
                personalNumber
        ));
        usersService.generateAndSetJwtToken(userEntity);

        return RegisterUserResponse.fromUserEntity(userEntity);
    }

}
