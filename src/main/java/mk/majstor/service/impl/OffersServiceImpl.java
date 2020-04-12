package mk.majstor.service.impl;

import mk.majstor.model.announcement.global.GlobalAnnouncement;
import mk.majstor.model.announcement.global.NewOfferGlobalAnnouncement;
import mk.majstor.model.exceptions.OfferNotFoundException;
import mk.majstor.model.exceptions.OglasNotFoundException;
import mk.majstor.model.exceptions.UserNotFoundException;
import mk.majstor.model.listing.Listing;
import mk.majstor.model.offer.Offer;
import mk.majstor.model.offer.OfferDto;
import mk.majstor.model.user.User;
import mk.majstor.repository.GlobalAnnouncementRepository;
import mk.majstor.repository.ListingRepository;
import mk.majstor.repository.OffersRepository;
import mk.majstor.repository.UserRepository;
import mk.majstor.service.OffersService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class OffersServiceImpl implements OffersService {

    private final OffersRepository offerRepository;
    private final ListingRepository listingRepository;
    private final UserRepository userRepository;
    private final GlobalAnnouncementRepository globalAnnouncementRepository;
    private final ModelMapper modelMapper;

    public OffersServiceImpl(
            OffersRepository offerRepository,
            ListingRepository listingRepository,
            UserRepository userRepository,
            GlobalAnnouncementRepository globalAnnouncementRepository,
            ModelMapper modelMapper) {
        this.offerRepository = offerRepository;
        this.listingRepository = listingRepository;
        this.userRepository = userRepository;
        this.globalAnnouncementRepository = globalAnnouncementRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Page<Offer> getAllOffersByListing(Long listingId, int pageNo, int pageSize) {

        Listing listing = listingRepository.findById(listingId).orElseThrow(OglasNotFoundException::new);
        Pageable paging = PageRequest.of(pageNo, pageSize);

        return offerRepository.findAllByListing(listing, paging);
    }

    @Override
    public Page<Offer> getAllOffersByListingByUser(Long listingId, Long userId, int pageNo, int pageSize) {
        Listing listing = listingRepository.findById(listingId).orElseThrow(OglasNotFoundException::new);
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        Pageable paging = PageRequest.of(pageNo, pageSize);

        return offerRepository.findAllByListingAndMadeBy(listing, user, paging);
    }

    @Override
    public Offer createOffer(OfferDto offerDto) {

        User user = userRepository.findById(offerDto.getMadeBy()).orElseThrow(UserNotFoundException::new);
        Listing listing = listingRepository.findById(offerDto.getOglasId()).orElseThrow(OglasNotFoundException::new);

        Offer offer = modelMapper.map(offerDto, Offer.class);
        offer.setMadeBy(user);
        offer.setListing(listing);
        offer.setId(null);

        //Global Announcement
        GlobalAnnouncement announcement = NewOfferGlobalAnnouncement.of(offer, listing);
        globalAnnouncementRepository.save(announcement);

        return offerRepository.save(offer);
    }

    @Override
    public Boolean deleteOffer(Long userId, Long offerId) {
        Offer offer = offerRepository.findById(offerId).orElseThrow(OfferNotFoundException::new);

        if(offer.getMadeBy().getId() != userId) throw new OfferNotFoundException();
        else offerRepository.delete(offer);

        return true;
    }

    @Override
    public Page<Offer> getAllOffers(int pageNo, int pageSize, String sortBy, String search) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));

        if(search.isEmpty()) return  offerRepository.findAll(paging);
        else return offerRepository.findAllByDescriptionContains(search, paging);
    }
}
