package mk.majstor.service;

import mk.majstor.model.deal.Deal;
import mk.majstor.model.deal.DealDto;
import mk.majstor.model.listing.Listing;
import mk.majstor.model.offer.Offer;
import org.springframework.data.domain.Page;

public interface DealService {

    Page<Deal> getDealsByUser(Long userId, String searchValue, int pageNo, int pageSize);

    Deal getDealByOglasId(Long oglasId);

    Deal createDeal(DealDto dealDto);

    Page<Deal> getAllDeals(int pageNo, int pageSize, String sortBy);

}
