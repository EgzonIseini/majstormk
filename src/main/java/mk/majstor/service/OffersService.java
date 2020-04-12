package mk.majstor.service;

import mk.majstor.model.listing.Listing;
import mk.majstor.model.offer.Offer;
import mk.majstor.model.offer.OfferDto;
import org.springframework.data.domain.Page;

public interface OffersService {

    Page<Offer> getAllOffersByListing(Long listingId, int pageNo, int pageSize);

    Page<Offer> getAllOffersByListingByUser(Long listingId, Long userId, int pageNo, int pageSize);

    Offer createOffer(OfferDto offer);

    Boolean deleteOffer(Long userId, Long offerId);

    Page<Offer> getAllOffers(int pageNo, int pageSize, String sortBy, String search);

}
