package ge.temojudo.digitalauction.repository.auctions;

import ge.temojudo.digitalauction.entity.auctions.AuctionEntity;
import ge.temojudo.digitalauction.model.auctions.AuctionDashboardView;
import ge.temojudo.digitalauction.model.auctions.getauctiondashboardviews.GetAuctionDashboardViewsRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AuctionsRepository extends CrudRepository<AuctionEntity, Long> {


    @Query("""
            SELECT NEW ge.temojudo.digitalauction.model.auctions.AuctionDashboardView (
                a.id,
                a.title,
                a.creationDate,
                a.startDate,
                a.status,
                a.registrationUser.username
            )
            FROM AuctionEntity a
            WHERE
                (COALESCE(:#{#request.titleContains}) IS NULL OR LOWER(a.title) LIKE CONCAT('%', LOWER(CAST(:#{#request.titleContains} AS text)), '%'))
                AND (COALESCE(:#{#request.startDateFrom}) IS NULL OR :#{#request.startDateFrom} <= a.startDate)
                AND (COALESCE(:#{#request.startDateTo}) IS NULL OR :#{#request.startDateTo} >= a.startDate)
                AND (COALESCE(:#{#request.status}) IS NULL OR :#{#request.status} = a.status)
            """)
    Page<AuctionDashboardView> getAuctionDashboardViews(GetAuctionDashboardViewsRequest request, Pageable pageable);

}
