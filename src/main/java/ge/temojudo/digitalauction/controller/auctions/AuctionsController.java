package ge.temojudo.digitalauction.controller.auctions;

import ge.temojudo.digitalauction.entity.users.UserEntity;
import ge.temojudo.digitalauction.model.auctions.getauction.GetAuctionRequest;
import ge.temojudo.digitalauction.model.auctions.getauction.GetAuctionResponse;
import ge.temojudo.digitalauction.model.auctions.registerauction.RegisterAuctionRequest;
import ge.temojudo.digitalauction.model.auctions.registerauction.RegisterAuctionResponse;
import ge.temojudo.digitalauction.service.auctions.AuctionsService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@Slf4j
@RestController
@RequestMapping(path = "auctions")
@AllArgsConstructor
@Validated
public class AuctionsController {

    private final AuctionsService auctionsService;

    @GetMapping("/{id}")
    public GetAuctionResponse getAuction(@PathVariable long id) {
        GetAuctionRequest request = new GetAuctionRequest(id);
        log.info("[getAuction] called with args {}", request);

        GetAuctionResponse response = auctionsService.getAuction(request);
        log.info("[getAuction] returned response {}", response);

        return response;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/register")
    public RegisterAuctionResponse registerAction(
            @Valid @RequestBody RegisterAuctionRequest request,
            Authentication authentication
    ) {
        log.info("[registerAction] called with args {}", request);
        request.setRegistrationUser((UserEntity) authentication.getPrincipal());

        RegisterAuctionResponse response = auctionsService.registerAuction(request);
        log.info("[registerAction] returned response {}", response);

        return response;
    }

}
