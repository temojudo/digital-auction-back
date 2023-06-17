package ge.temojudo.digitalauction.model.auctions;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;


@Data
@AllArgsConstructor
public class AuctionDashboardView {
    private long id;
    private String title;
    private Date creationDate;
    private Date startDate;
    private String status;
    private String registrationUsername;
}
