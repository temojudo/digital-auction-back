package ge.temojudo.digitalauction.model.auctions.registerauction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import ge.temojudo.digitalauction.entity.users.UserEntity;
import lombok.Data;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import java.util.Date;


@Data
public class RegisterAuctionRequest {

    @NotNull
    private String title;
    private String description;
    private String photoId;
    @NotNull
    @Future
    private Date startDateUtc;

    @JsonIgnore
    private UserEntity registrationUser;

}
