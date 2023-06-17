package ge.temojudo.digitalauction.repository.auctions;

import ge.temojudo.digitalauction.entity.auctions.AuctionEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AuctionsRepository extends CrudRepository<AuctionEntity, Long> {
}
