package ge.temojudo.digitalauction.model.auctions;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class AuctionDashboardView {
    private long id;
    private String title;
    private String creationDate;
    private String startDate;
    private String status;
    private String registrationUsername;
}
