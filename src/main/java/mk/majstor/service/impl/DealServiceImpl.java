package mk.majstor.service.impl;

import mk.majstor.model.announcement.global.DealReachedGlobalAnnouncement;
import mk.majstor.model.announcement.global.GlobalAnnouncement;
import mk.majstor.model.deal.Deal;
import mk.majstor.model.deal.DealDto;
import mk.majstor.model.exceptions.DealNotFoundException;
import mk.majstor.model.exceptions.OglasNotFoundException;
import mk.majstor.model.exceptions.UserNotFoundException;
import mk.majstor.model.listing.Listing;
import mk.majstor.model.offer.Offer;
import mk.majstor.model.user.PrivateUser;
import mk.majstor.model.user.User;
import mk.majstor.repository.DealsRepository;
import mk.majstor.repository.GlobalAnnouncementRepository;
import mk.majstor.repository.ListingRepository;
import mk.majstor.repository.UserRepository;
import mk.majstor.service.DealService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;

@Service
public class DealServiceImpl implements DealService {

    private DealsRepository repository;
    private UserRepository userRepository;
    private ListingRepository listingRepository;
    private GlobalAnnouncementRepository globalAnnouncementRepository;

    public DealServiceImpl(
            DealsRepository repository,
            UserRepository userRepository,
            ListingRepository listingRepository,
            GlobalAnnouncementRepository globalAnnouncementRepository)
    {
        this.repository = repository;
        this.userRepository = userRepository;
        this.listingRepository = listingRepository;
        this.globalAnnouncementRepository = globalAnnouncementRepository;
    }

    @Override
    public Page<Deal> getDealsByUser(Long userId, String searchValue, int pageNo, int pageSize) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);

        Pageable paging = PageRequest.of(pageNo, pageSize);

        if(searchValue.isEmpty()) return  repository.findAllByUser(user, paging);
        else return repository.findAllByUserAndListingNameContains(user, searchValue, paging);
    }

    @Override
    public Deal getDealByOglasId(Long oglasId) {
        return repository.getByListingId(oglasId).orElseThrow(DealNotFoundException::new);
    }

    @Override
    public Deal createDeal(DealDto dealDto) {

        Listing listing = listingRepository.findById(dealDto.getListingId()).orElseThrow(OglasNotFoundException::new);
        User user = userRepository.findById(dealDto.getUserId()).orElseThrow(UserNotFoundException::new);
        User madeBy = userRepository.findById(dealDto.getMadeById()).orElseThrow(UserNotFoundException::new);

        Deal deal = repository.save(new Deal((PrivateUser) user, madeBy, listing, dealDto.getPrice(), Timestamp.from(Instant.now())));

        listing.setActive(false);
        listing.setDeal(deal);
        listingRepository.save(listing);

        //Announcement
        GlobalAnnouncement announcement = DealReachedGlobalAnnouncement.of(listing, madeBy);
        globalAnnouncementRepository.save(announcement);

        return deal;
    }

    @Override
    public Page<Deal> getAllDeals(int pageNo, int pageSize, String sortBy) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));

        return repository.findAll(paging);
    }

}
