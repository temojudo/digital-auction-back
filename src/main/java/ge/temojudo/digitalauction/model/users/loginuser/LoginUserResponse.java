package ge.temojudo.digitalauction.model.users.loginuser;

import ge.temojudo.digitalauction.entity.users.UserEntity;
import ge.temojudo.digitalauction.model.users.UserInfo;
import lombok.AllArgsConstructor;
import lombok.Data;


@AllArgsConstructor
@Data
public class LoginUserResponse {

    private UserInfo userInfo;
    private String jwt;

    public static LoginUserResponse fromUserEntity(UserEntity userEntity) {
        return new LoginUserResponse(UserInfo.fromUserEntity(userEntity), userEntity.getJwt());
    }

}
