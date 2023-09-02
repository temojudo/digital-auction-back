package ge.temojudo.digitalauction.service.auctions;

import ge.temojudo.digitalauction.entity.auctions.AuctionEntity;
import ge.temojudo.digitalauction.entity.users.UserEntity;
import ge.temojudo.digitalauction.exceptions.invalidargument.InvalidArgumentException;
import ge.temojudo.digitalauction.exceptions.resourcenotfound.ResourceNotFoundException;
import ge.temojudo.digitalauction.model.auctions.ActionDashboardSortParamName;
import ge.temojudo.digitalauction.model.auctions.AuctionDashboardView;
import ge.temojudo.digitalauction.model.auctions.AuctionStatus;
import ge.temojudo.digitalauction.model.auctions.getauction.GetAuctionRequest;
import ge.temojudo.digitalauction.model.auctions.getauction.GetAuctionResponse;
import ge.temojudo.digitalauction.model.auctions.getauctiondashboardviews.GetAuctionDashboardViewsRequest;
import ge.temojudo.digitalauction.model.auctions.getauctiondashboardviews.GetAuctionDashboardViewsResponse;
import ge.temojudo.digitalauction.model.auctions.registerauction.RegisterAuctionRequest;
import ge.temojudo.digitalauction.model.auctions.registerauction.RegisterAuctionResponse;
import ge.temojudo.digitalauction.model.bids.placebid.PlaceBidRequest;
import ge.temojudo.digitalauction.model.bids.placebid.PlaceBidResponse;
import ge.temojudo.digitalauction.repository.auctions.AuctionsRepository;
import ge.temojudo.digitalauction.security.JwtUtils;
import ge.temojudo.digitalauction.service.bidlogs.BidLogsService;
import ge.temojudo.digitalauction.service.bids.BidsService;
import ge.temojudo.digitalauction.service.users.UsersService;
import lombok.AllArgsConstructor;
import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;


@Service
@AllArgsConstructor
public class AuctionsService {

    private final AuctionsRepository auctionsRepository;

    private final BidsService bidsService;
    private final BidLogsService bidLogsService;
    private final JwtUtils jwtUtils;
    private final UsersService usersService;

    public GetAuctionResponse getAuction(GetAuctionRequest request) {
        return GetAuctionResponse.fromAuctionEntity(getAuctionEntityById(request.getId()));
    }

    public RegisterAuctionResponse registerAuction(RegisterAuctionRequest request) {
        AuctionEntity auction = auctionsRepository.save(new AuctionEntity(
                request.getStartingBid(),
                request.getTitle(),
                request.getDescription(),
                request.getPhotoId(),
                new Date(),
                request.getStartDateUtc(),
                AuctionStatus.CREATED.name(),
                request.getRegistrationUser()
        ));

        return RegisterAuctionResponse.fromAuctionEntity(auction);
    }

    public GetAuctionDashboardViewsResponse getAuctionDashboardViews(GetAuctionDashboardViewsRequest request) {
        Sort sortParam = getSortParam(request.getSortBy(), request.getSortByDirection());
        Pageable pageable = PageRequest.of(request.getPageNumber(), request.getPageSize(), sortParam);

        Page<AuctionDashboardView> auctionDashboardViews = auctionsRepository.getAuctionDashboardViews(request, pageable);

        return new GetAuctionDashboardViewsResponse(
                auctionDashboardViews.getNumberOfElements(),
                auctionDashboardViews.getTotalElements(),
                auctionDashboardViews.getTotalPages(),
                auctionDashboardViews.getContent()
        );
    }

    @Transactional
    public PlaceBidResponse placeBid(PlaceBidRequest request) throws AuthenticationException {
        AuctionEntity auction = getAuctionEntityById(request.getAuctionId());

        String token = request.getUserJwt().substring(7);

        String username = jwtUtils.extractUsername(token);
        UserEntity user = usersService.loadUserByUsername(username);

        if (!jwtUtils.validateToken(token, user)) {
            throw new AuthenticationException("Invalid token");
        }

        if (Objects.equals(auction.getRegistrationUser().getId(), user.getId())) {
            throw new InvalidArgumentException(
                    "auctionId",
                    String.format("couldn't place bid by registration user on auction with id [%d]", request.getAuctionId())
            );
        }

        if (AuctionStatus.FINISHED.name().equals(auction.getStatus())) {
            throw new InvalidArgumentException(
                    "auctionId",
                    String.format("auction with id [%d] must not be finished", request.getAuctionId())
            );
        }

        Date dateNow = new Date();
        if (auction.getStartDate().after(dateNow)) {
            throw new InvalidArgumentException(
                    "auctionId",
                    String.format("auction with id [%d] has not started yet", request.getAuctionId())
            );
        }

        if (request.getBidValue() <= auction.getCurrentBid()) {
            throw new InvalidArgumentException(
                    "bidValue",
                    String.format("auction's new bid must be greater than [%f]", auction.getCurrentBid())
            );
        }

        if (user.getBidCount() < request.getBidValue()) {
            throw new InvalidArgumentException("bidValue", "don't have any bids left");
        }

        auction.setCurrentBid(request.getBidValue());
        auctionsRepository.save(auction);

        bidsService.spendBid(user, request.getBidValue());
        bidLogsService.addBidLog(request.getBidValue(), auction, user);

        return new PlaceBidResponse(request.getBidValue());
    }

    private Sort getSortParam(String sortBy, String sortByDirection) {
        if (sortBy == null) {
            return Sort.by(new ArrayList<>());
        }

        String orderByName = switch (ActionDashboardSortParamName.valueOf(sortBy)) {
            case CREATION_DATE -> "creationDate";
            case START_DATE -> "startDate";
            case STATUS -> "status";
        };

        return Sort.Direction.DESC.name().equals(sortByDirection)
                ? Sort.by(orderByName).descending()
                : Sort.by(orderByName).ascending();
    }

    public AuctionEntity getAuctionEntityById(long id) {
        Optional<AuctionEntity> optionalAuction = findById(id);
        if (optionalAuction.isEmpty()) {
            throw new ResourceNotFoundException(AuctionEntity.class.getName(), "id", id);
        }

        return optionalAuction.get();
    }

    public Optional<AuctionEntity> findById(long id) {
        return auctionsRepository.findById(id);
    }

}
