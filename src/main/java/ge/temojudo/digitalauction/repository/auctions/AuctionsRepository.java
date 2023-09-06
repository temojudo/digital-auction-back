package ge.temojudo.digitalauction.repository.auctions;

import ge.temojudo.digitalauction.entity.auctions.AuctionEntity;
import ge.temojudo.digitalauction.model.auctions.AuctionDashboardView;
import ge.temojudo.digitalauction.model.auctions.getauctiondashboardviews.GetAuctionDashboardViewsRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;


@Repository
public interface AuctionsRepository extends CrudRepository<AuctionEntity, Long> {


    @Query("""
            SELECT NEW ge.temojudo.digitalauction.model.auctions.AuctionDashboardView (
                a.id,
                a.title,
                TO_CHAR(a.creationDate, 'DD/MM/YYYY HH24:MI:SS'),
                TO_CHAR(a.startDate, 'DD/MM/YYYY HH24:MI:SS'),
                a.status,
                ru.username
            )
            FROM AuctionEntity a
            LEFT JOIN a.registrationUser ru
            LEFT JOIN a.buyer bu
            WHERE
                (COALESCE(:#{#request.titleContains}) IS NULL OR LOWER(a.title) LIKE CONCAT('%', LOWER(CAST(:#{#request.titleContains} AS text)), '%'))
                AND (COALESCE(:#{#request.startDateFrom}) IS NULL OR :#{#request.startDateFrom} <= a.startDate)
                AND (COALESCE(:#{#request.startDateTo}) IS NULL OR :#{#request.startDateTo} >= a.startDate)
                AND (COALESCE(:#{#request.status}) IS NULL OR :#{#request.status} = a.status)
                AND (COALESCE(:#{#request.registrationUsername}) IS NULL OR :#{#request.registrationUsername} = ru.username)
                AND (COALESCE(:#{#request.buyerUsername}) IS NULL OR :#{#request.buyerUsername} = bu.username)
            """)
    Page<AuctionDashboardView> getAuctionDashboardViews(GetAuctionDashboardViewsRequest request, Pageable pageable);

    @Query("""
            SELECT a
            FROM AuctionEntity a
            WHERE
                (a.status != 'FINISHED')
                AND (EXTRACT(EPOCH FROM (:dateNow - a.startDate)) >= 20)
                AND (
                    NOT EXISTS (SELECT 1 FROM BidLogEntity bl WHERE bl.auction = a)
                    OR EXTRACT(
                        EPOCH FROM (:dateNow - (SELECT MAX(bl.date) FROM BidLogEntity bl WHERE bl.auction = a))
                    ) >= 20
                )
            """)
    List<AuctionEntity> getAuctionsShouldBeCompletedButIsNot(Date dateNow);

    @Modifying
    @Query("""
            UPDATE AuctionEntity a
            SET
                a.status = 'FINISHED',
                a.buyer = (
                    SELECT bl.bidUser
                    FROM BidLogEntity bl
                    WHERE
                        (bl.auction = a)
                        AND (bl.date = (SELECT MAX(bli.date) FROM BidLogEntity bli where bli.auction = :auction))
                )
            WHERE a = :auction
            """)
    void updateAuctionAfterCompletion(AuctionEntity auction);

}
