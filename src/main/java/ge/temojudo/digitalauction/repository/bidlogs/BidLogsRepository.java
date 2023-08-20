package ge.temojudo.digitalauction.repository.bidlogs;

import ge.temojudo.digitalauction.entity.bidlogs.BidLogEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface BidLogsRepository extends CrudRepository<BidLogEntity, Long> {
}
