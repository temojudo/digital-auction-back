package ge.temojudo.digitalauction.controller.bids;

import ge.temojudo.digitalauction.entity.users.UserEntity;
import ge.temojudo.digitalauction.model.bids.buybid.BuyBidRequest;
import ge.temojudo.digitalauction.model.bids.buybid.BuyBidResponse;
import ge.temojudo.digitalauction.service.bids.BidsService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@Slf4j
@RestController
@RequestMapping(path = "bids")
@AllArgsConstructor
@Validated
public class BidsController {

    private final BidsService bidsService;

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/buy")
    public BuyBidResponse buyBid(
            @Valid @RequestBody BuyBidRequest request,
            Authentication authentication
    ) {
        log.info("[buyBid] called with args {}", request);
        request.setUser((UserEntity) authentication.getPrincipal());

        BuyBidResponse response = bidsService.buyBid(request);
        log.info("[buyBid] returned response {}", response);

        return response;
    }

}
