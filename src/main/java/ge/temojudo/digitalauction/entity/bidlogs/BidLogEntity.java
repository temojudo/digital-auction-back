package ge.temojudo.digitalauction.entity.bidlogs;

import ge.temojudo.digitalauction.entity.auctions.AuctionEntity;
import ge.temojudo.digitalauction.entity.users.UserEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;


@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@Entity
@Table(name = "bid_logs")
public class BidLogEntity {

    @Id
    @SequenceGenerator(
            name = "bid_logs_seq",
            sequenceName = "bid_logs_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            generator = "bid_logs_seq",
            strategy = GenerationType.SEQUENCE
    )
    private Long id;

    private Date date;

    private Double bidValue;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private AuctionEntity auction;

    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    private UserEntity bidUser;

    public BidLogEntity(
            Date date,
            Double bidValue,
            AuctionEntity auction,
            UserEntity bidUser
    ) {
        this.date = date;
        this.bidValue = bidValue;
        this.auction = auction;
        this.bidUser = bidUser;
    }

}
