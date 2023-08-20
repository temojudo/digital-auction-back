package ge.temojudo.digitalauction.service.bidlogs;

import ge.temojudo.digitalauction.entity.auctions.AuctionEntity;
import ge.temojudo.digitalauction.entity.bidlogs.BidLogEntity;
import ge.temojudo.digitalauction.entity.users.UserEntity;
import ge.temojudo.digitalauction.repository.bidlogs.BidLogsRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;


@Service
@AllArgsConstructor
public class BidLogsService {

    private final BidLogsRepository bidLogsRepository;

    public void addBidLog(Double bidValue, AuctionEntity auction, UserEntity user) {
        bidLogsRepository.save(new BidLogEntity(
                new Date(),
                bidValue,
                auction,
                user
        ));
    }

}
