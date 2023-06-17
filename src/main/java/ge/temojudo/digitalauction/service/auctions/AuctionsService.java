package ge.temojudo.digitalauction.service.auctions;

import ge.temojudo.digitalauction.entity.auctions.AuctionEntity;
import ge.temojudo.digitalauction.exceptions.resourcenotfound.ResourceNotFoundException;
import ge.temojudo.digitalauction.model.auctions.AuctionStatus;
import ge.temojudo.digitalauction.model.auctions.getauction.GetAuctionRequest;
import ge.temojudo.digitalauction.model.auctions.getauction.GetAuctionResponse;
import ge.temojudo.digitalauction.model.auctions.registerauction.RegisterAuctionRequest;
import ge.temojudo.digitalauction.model.auctions.registerauction.RegisterAuctionResponse;
import ge.temojudo.digitalauction.repository.auctions.AuctionsRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

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
