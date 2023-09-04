package ge.temojudo.digitalauction.model.bids.placebid;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.validation.constraints.NotNull;


@Data
public class PlaceBidRequest {

    @JsonIgnore
    private long auctionId;
    @NotNull
    private Double bidValue;
    @NotNull
    private String userJwt;

}
