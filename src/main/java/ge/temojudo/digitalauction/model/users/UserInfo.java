package ge.temojudo.digitalauction.model.users;

import ge.temojudo.digitalauction.entity.users.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;


@AllArgsConstructor
@Data
public class UserInfo {

    private String firstname;
    private String lastname;
    private String username;
    private String personalNumber;

    public static UserInfo fromUserEntity(UserEntity userEntity) {
        return new UserInfo(
                userEntity.getFirstname(),
                userEntity.getLastname(),
                userEntity.getUsername(),
                userEntity.getPersonalNumber()
        );
    }

}
