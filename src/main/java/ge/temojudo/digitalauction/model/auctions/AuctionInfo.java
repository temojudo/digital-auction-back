package ge.temojudo.digitalauction.model.auctions;

import ge.temojudo.digitalauction.entity.auctions.AuctionEntity;
import lombok.AllArgsConstructor;
import lombok.Data;


@AllArgsConstructor
@Data
public class AuctionInfo {

    private long id;
    private Double currentBid;
    private String title;
    private String description;
    private String photoId;
    private String creationDate;
    private String startDate;
    private String status;
    private String registrationUsername;
    private String buyerUsername;

    public static AuctionInfo fromAuctionEntity(AuctionEntity auction) {
        return new AuctionInfo(
                auction.getId(),
                auction.getCurrentBid(),
                auction.getTitle(),
                auction.getDescription(),
                auction.getPhotoId(),
                auction.getCreationDate().toString(),
                auction.getStartDate().toString(),
                auction.getStatus(),
                auction.getRegistrationUser().getUsername(),
                auction.getBuyer() == null ? null : auction.getBuyer().getUsername()
        );
    }

}
