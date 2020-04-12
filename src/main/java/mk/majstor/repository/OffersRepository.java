package mk.majstor.repository;

import mk.majstor.model.listing.Listing;
import mk.majstor.model.offer.Offer;
import mk.majstor.model.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OffersRepository extends JpaRepository<Offer, Long> {

    Page<Offer> findAllByListing(Listing listing, Pageable pageable);

    Page<Offer> findAllByListingAndMadeBy(Listing listing, User user, Pageable pageable);

    Page<Offer> findAllByDescriptionContains(String search, Pageable pageable);
    
}
