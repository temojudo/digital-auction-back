package ge.temojudo.digitalauction.model.users.registeruser;

import ge.temojudo.digitalauction.entity.users.UserEntity;
import ge.temojudo.digitalauction.model.users.UserInfo;
import lombok.AllArgsConstructor;
import lombok.Data;


@AllArgsConstructor
@Data
public class RegisterUserResponse {

    private UserInfo userInfo;
    private String jwt;

    public static RegisterUserResponse fromUserEntity(UserEntity userEntity) {
        return new RegisterUserResponse(UserInfo.fromUserEntity(userEntity), userEntity.getJwt());
    }

}
