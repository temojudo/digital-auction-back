package ge.temojudo.digitalauction.service.users;

import ge.temojudo.digitalauction.entity.users.UserEntity;
import ge.temojudo.digitalauction.exceptions.resourcenotfound.ResourceNotFoundException;
import ge.temojudo.digitalauction.repository.users.UsersRepository;
import ge.temojudo.digitalauction.security.JwtUtils;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@AllArgsConstructor
public class UsersService implements UserDetailsService {

    private final UsersRepository usersRepository;

    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    public void generateAndSetJwtToken(UserEntity userEntity) {
        String jwt = "Bearer " + jwtUtils.generateToken(userEntity);
        userEntity.setJwt(jwt);
    }

    public boolean existsByUsernameOrPersonalNumber(String username, String personalNumber) {
        return usersRepository.existsByUsernameOrPersonalNumber(username, personalNumber);
    }

    public UserEntity getUserByUsername(String username) {
        Optional<UserEntity> optionalUserEntity = usersRepository.findByUsername(username);
        if (optionalUserEntity.isEmpty()) {
            throw new ResourceNotFoundException(UserEntity.class.getName(), "username", username);
        }

        return optionalUserEntity.get();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return getUserByUsername(username);
    }

    public UserEntity findByUsernameAndPassword(String username, String password) {
        UserEntity userEntity = getUserByUsername(username);
        if (!passwordEncoder.matches(password, userEntity.getPassword())) {
            throw new ResourceNotFoundException(UserEntity.class.getName(), "password", password);
        }

        return userEntity;
    }

    public UserEntity save(UserEntity userEntity) {
        return usersRepository.save(userEntity);
    }

}
