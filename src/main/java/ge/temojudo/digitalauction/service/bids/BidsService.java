package ge.temojudo.digitalauction.service.bids;

import ge.temojudo.digitalauction.model.bids.buybid.BuyBidRequest;
import ge.temojudo.digitalauction.model.bids.buybid.BuyBidResponse;
import ge.temojudo.digitalauction.service.users.UsersService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;


@Service
@AllArgsConstructor
public class BidsService {

    private final UsersService usersService;

    @Transactional
    public BuyBidResponse buyBid(BuyBidRequest request) {
        // TODO: do money transaction

        Double newBidCount = usersService.addUserBids(request.getUser(), request.getBidCount());

        return new BuyBidResponse(newBidCount);
    }

}
