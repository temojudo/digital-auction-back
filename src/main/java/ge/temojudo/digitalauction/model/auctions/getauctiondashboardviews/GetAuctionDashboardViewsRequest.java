package ge.temojudo.digitalauction.model.auctions.getauctiondashboardviews;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;


@Data
@AllArgsConstructor
public class GetAuctionDashboardViewsRequest {

    private int pageSize;
    private int pageNumber;

    private String titleContains;
    private Date startDateFrom;
    private Date startDateTo;
    private String status;

    private String sortBy;
    private String sortByDirection;

}
