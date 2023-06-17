package ge.temojudo.digitalauction.model.auctions.getauction;

import ge.temojudo.digitalauction.entity.auctions.AuctionEntity;
import ge.temojudo.digitalauction.model.auctions.AuctionInfo;
import lombok.AllArgsConstructor;
import lombok.Data;


@AllArgsConstructor
@Data
public class GetAuctionResponse {

    private AuctionInfo auctionInfo;

    public static GetAuctionResponse fromAuctionEntity(AuctionEntity auctionEntity) {
        return new GetAuctionResponse(AuctionInfo.fromAuctionEntity(auctionEntity));
    }

}
