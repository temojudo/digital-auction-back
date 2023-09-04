package ge.temojudo.digitalauction.scheduler;

import ge.temojudo.digitalauction.service.auctions.AuctionsService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Slf4j
@AllArgsConstructor
@Component
public class ScheduledComponent {

    private final AuctionsService auctionsService;

    @Scheduled(fixedDelay = 5000)
    public void publishUpdates() {
        auctionsService.checkCompleteAuctions();
    }

}
