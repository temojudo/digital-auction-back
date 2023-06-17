package ge.temojudo.digitalauction.service.auctions;

import ge.temojudo.digitalauction.entity.auctions.AuctionEntity;
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
import ge.temojudo.digitalauction.repository.auctions.AuctionsRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;


@Service
@AllArgsConstructor
public class AuctionsService {

    private final AuctionsRepository auctionsRepository;

    public GetAuctionResponse getAuction(GetAuctionRequest request) {
        return GetAuctionResponse.fromAuctionEntity(getAuctionEntityById(request.getId()));
    }

    public RegisterAuctionResponse registerAuction(RegisterAuctionRequest request) {
        AuctionEntity auction = auctionsRepository.save(new AuctionEntity(
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
