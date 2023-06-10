package ge.temojudo.digitalauction.controller.users;

import ge.temojudo.digitalauction.model.users.loginuser.LoginUserRequest;
import ge.temojudo.digitalauction.model.users.loginuser.LoginUserResponse;
import ge.temojudo.digitalauction.model.users.registeruser.RegisterUserRequest;
import ge.temojudo.digitalauction.model.users.registeruser.RegisterUserResponse;
import ge.temojudo.digitalauction.service.users.LoginService;
import ge.temojudo.digitalauction.service.users.RegistrationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@Slf4j
@RestController
@RequestMapping(path = "users")
@AllArgsConstructor
@Validated
public class UsersController {

    private final LoginService loginService;
    private final RegistrationService registrationService;

    @PostMapping("/login")
    public LoginUserResponse login(@Valid @RequestBody LoginUserRequest request) {
        log.info("[login] called with args {}", request);

        LoginUserResponse response = loginService.login(request);
        log.info("[login] returned response {}", response);

        return response;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/register")
    public RegisterUserResponse register(@Valid @RequestBody RegisterUserRequest request) {
        log.info("[register] called with args {}", request);

        RegisterUserResponse response = registrationService.register(request);
        log.info("[register] returned response {}", response);

        return response;
    }

}
