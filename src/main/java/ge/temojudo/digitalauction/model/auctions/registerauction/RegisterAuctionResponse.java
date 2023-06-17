package ge.temojudo.digitalauction.model.auctions.registerauction;

import ge.temojudo.digitalauction.entity.auctions.AuctionEntity;
import ge.temojudo.digitalauction.model.auctions.AuctionInfo;
import lombok.AllArgsConstructor;
import lombok.Data;


@AllArgsConstructor
@Data
public class RegisterAuctionResponse {

    private AuctionInfo auctionInfo;

    public static RegisterAuctionResponse fromAuctionEntity(AuctionEntity auctionEntity) {
        return new RegisterAuctionResponse(AuctionInfo.fromAuctionEntity(auctionEntity));
    }

}
