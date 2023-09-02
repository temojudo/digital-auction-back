package ge.temojudo.digitalauction.model.bids.placebid;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;


@Data
public class PlaceBidRequest {

    @JsonIgnore
    private long auctionId;
    private Double bidValue;
    private String userJwt;

}
