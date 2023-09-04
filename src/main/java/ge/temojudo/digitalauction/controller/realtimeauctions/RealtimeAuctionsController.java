package ge.temojudo.digitalauction.controller.realtimeauctions;

import ge.temojudo.digitalauction.model.bids.placebid.PlaceBidRequest;
import ge.temojudo.digitalauction.model.bids.placebid.PlaceBidResponse;
import ge.temojudo.digitalauction.service.auctions.AuctionsService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;


@Slf4j
@Controller
@AllArgsConstructor
@Validated
public class RealtimeAuctionsController {

    private final AuctionsService auctionsService;

    @MessageMapping("/auctions/{auctionId}/placeBid")
    @SendTo("/topic/auctions/{auctionId}")
    public PlaceBidResponse placeBid(
            @DestinationVariable("auctionId") long auctionId,
            @Valid @Payload PlaceBidRequest request
    ) throws AuthenticationException {
        request.setAuctionId(auctionId);
        log.info("[placeBid] called with args {}", request);

        PlaceBidResponse response = auctionsService.placeBid(request);
        log.info("[placeBid] returned response {}", response);

        return response;
    }

}
