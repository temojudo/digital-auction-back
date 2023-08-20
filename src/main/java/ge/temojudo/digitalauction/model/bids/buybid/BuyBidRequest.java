package ge.temojudo.digitalauction.model.bids.buybid;

import com.fasterxml.jackson.annotation.JsonIgnore;
import ge.temojudo.digitalauction.entity.users.UserEntity;
import lombok.Data;

import javax.validation.constraints.Positive;


@Data
public class BuyBidRequest {

    private String cardNumber;
    private String expDate;
    private String ccv;
    @Positive
    private Double bidCount;

    @JsonIgnore
    private UserEntity user;

}
