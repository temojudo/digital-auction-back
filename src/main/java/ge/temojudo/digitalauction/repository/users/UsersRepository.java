package ge.temojudo.digitalauction.repository.users;

import ge.temojudo.digitalauction.entity.users.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UsersRepository extends CrudRepository<UserEntity, Long> {

    Optional<UserEntity> findByUsername(String username);

    boolean existsByUsernameOrPersonalNumber(String username, String personalNUmber);

}
