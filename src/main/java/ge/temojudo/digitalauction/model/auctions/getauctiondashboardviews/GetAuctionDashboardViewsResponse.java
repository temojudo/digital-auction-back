package ge.temojudo.digitalauction.model.auctions.getauctiondashboardviews;

import ge.temojudo.digitalauction.model.auctions.AuctionDashboardView;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;


@Data
@AllArgsConstructor
public class GetAuctionDashboardViewsResponse {

    private Integer itemCount;
    private Long totalItemCount;
    private Integer totalPageCount;
    private List<AuctionDashboardView> auctionDashboardViews;

}
