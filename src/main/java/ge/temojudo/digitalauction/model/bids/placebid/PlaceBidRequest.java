package ge.temojudo.digitalauction.model.bids.placebid;

import com.fasterxml.jackson.annotation.JsonIgnore;
import ge.temojudo.digitalauction.entity.users.UserEntity;
import lombok.Data;

import javax.validation.constraints.NotNull;


@Data
public class PlaceBidRequest {

    @JsonIgnore
    private long auctionId;
    @NotNull
    private Double bidValue;
    @JsonIgnore
    private UserEntity user;

}
