package ge.temojudo.digitalauction.service.users;

import ge.temojudo.digitalauction.entity.users.UserEntity;
import ge.temojudo.digitalauction.model.users.loginuser.LoginUserRequest;
import ge.temojudo.digitalauction.model.users.loginuser.LoginUserResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class LoginService {

    private final UsersService usersService;
    private final AuthenticationManager authenticationManager;

    public LoginUserResponse login(LoginUserRequest request) {
        String username = request.getUsername();
        String password = request.getPassword();

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        UserEntity userEntity = usersService.findByUsernameAndPassword(username, password);
        usersService.generateAndSetJwtToken(userEntity);

        return LoginUserResponse.fromUserEntity(userEntity);
    }

}
