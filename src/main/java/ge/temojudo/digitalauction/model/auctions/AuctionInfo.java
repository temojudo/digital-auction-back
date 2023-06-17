package ge.temojudo.digitalauction.model.auctions;

import ge.temojudo.digitalauction.entity.auctions.AuctionEntity;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;


@AllArgsConstructor
@Data
public class AuctionInfo {

    private long id;
    private String title;
    private String description;
    private String photoId;
    private Date creationDate;
    private Date startDate;
    private String status;
    private String registrationUsername;

    public static AuctionInfo fromAuctionEntity(AuctionEntity auction) {
        return new AuctionInfo(
                auction.getId(),
                auction.getTitle(),
                auction.getDescription(),
                auction.getPhotoId(),
                auction.getCreationDate(),
                auction.getStartDate(),
                auction.getStatus(),
                auction.getRegistrationUser().getUsername()
        );
    }

}
