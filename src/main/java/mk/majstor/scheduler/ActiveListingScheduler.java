package mk.majstor.scheduler;

import mk.majstor.model.listing.Listing;
import mk.majstor.repository.ListingRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class ActiveListingScheduler {

    private final ListingRepository listingRepository;

    public ActiveListingScheduler(ListingRepository listingRepository) {
        this.listingRepository = listingRepository;
    }

    @Scheduled(cron = "0 0 0 * * *", zone = "")
    public void checkActiveListings() {
        listingRepository.deactivateOglasiOlderThan(LocalDate.now());
    }

}
