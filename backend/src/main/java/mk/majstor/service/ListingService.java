package mk.majstor.service;

import mk.majstor.model.listing.Listing;
import mk.majstor.model.listing.ListingDto;
import org.springframework.data.domain.Page;

public interface ListingService {

    Page<Listing> getAllOglasi(int pageNo, int pageSize, String sortBy, String search);

    Listing getOglasById(Long id);

    Listing saveOglas(ListingDto listingDto);

    Page<Listing> getOglasiByUser(Long userId, String searchValue, int pageNo, int pageSize);

    Page<Listing> getActiveOglasiByUser(Long userId, String searchValue, int pageNo, int pageSize);

    Page<Listing> getAllUnsuccessfulOglasiByUser(Long userId, int pageNo, int pageSize, String search);

    Page<Listing> getAllSuccessfulOglasiByUser(Long userId, int pageNo, int pageSize, String search);

    Listing modifyOglas(Long oglasId, ListingDto listingDto);

    Listing deleteOglas(Long oglasId);
}
