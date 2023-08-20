package ge.temojudo.digitalauction.service.auctions;

import ge.temojudo.digitalauction.entity.auctions.AuctionEntity;
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
import ge.temojudo.digitalauction.repository.auctions.AuctionsRepository;
import ge.temojudo.digitalauction.service.bidlogs.BidLogsService;
import ge.temojudo.digitalauction.service.bids.BidsService;
import lombok.AllArgsConstructor;
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
    public void placeBid(PlaceBidRequest request) {
        AuctionEntity auction = getAuctionEntityById(request.getAuctionId());

        if (Objects.equals(auction.getRegistrationUser().getId(), request.getUser().getId())) {
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

        if (request.getUser().getBidCount() < request.getBidValue()) {
            throw new InvalidArgumentException("bidValue", "don't have any bids left");
        }

        bidsService.spendBid(request.getUser());
        bidLogsService.addBidLog(request.getBidValue(), auction, request.getUser());
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
