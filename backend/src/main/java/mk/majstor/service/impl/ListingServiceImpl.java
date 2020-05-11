package mk.majstor.service.impl;

import mk.majstor.model.announcement.global.GlobalAnnouncement;
import mk.majstor.model.announcement.global.NewListingGlobalAnnouncement;
import mk.majstor.model.exceptions.OglasNotFoundException;
import mk.majstor.model.exceptions.UserNotFoundException;
import mk.majstor.model.listing.Listing;
import mk.majstor.model.listing.ListingDto;
import mk.majstor.model.user.User;
import mk.majstor.repository.GlobalAnnouncementRepository;
import mk.majstor.repository.ListingRepository;
import mk.majstor.repository.UserRepository;
import mk.majstor.service.ListingService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class ListingServiceImpl implements ListingService {

    private final ListingRepository repository;
    private final UserRepository userRepository;
    private final GlobalAnnouncementRepository globalAnnouncementRepository;
    private final ModelMapper modelMapper;

    public ListingServiceImpl(
            ListingRepository repository,
            UserRepository userRepository,
            GlobalAnnouncementRepository globalAnnouncementRepository,
            ModelMapper modelMapper) {
        this.repository = repository;
        this.userRepository = userRepository;
        this.globalAnnouncementRepository = globalAnnouncementRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Page<Listing> getAllOglasi(int pageNo, int pageSize, String sortBy, String searchValue) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));

        if(searchValue.isEmpty()) return  repository.findAll(paging);
        else return repository.findAllByNameContains(searchValue, paging);
    }


    @Override
    public Listing getOglasById(Long id) {
        Listing listing = repository.findById(id).orElseThrow(OglasNotFoundException::new);
        if(listing.isDeletedFlag()) throw new OglasNotFoundException();
        return listing;
    }

    @Override
    public Listing saveOglas(ListingDto listingDto) {

        User user = userRepository.findById(listingDto.getPostedBy()).orElseThrow(UserNotFoundException::new);
        Listing listing = modelMapper.map(listingDto, Listing.class);
        listing.setPostedBy(user);


        listing = repository.save(listing);
        //Announcement
        GlobalAnnouncement announcement = NewListingGlobalAnnouncement.of(listing);
        globalAnnouncementRepository.save(announcement);

        return listing;
    }

    @Override
    public Page<Listing> getOglasiByUser(Long userId, String searchValue, int pageNo, int pageSize) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);

        Pageable paging = PageRequest.of(pageNo, pageSize);

        if(searchValue.isEmpty()) return  repository.findAllByPostedBy(user, paging);
        else return repository.findByPostedByAndNameContains(user, searchValue, paging);
    }

    @Override
    public Page<Listing> getActiveOglasiByUser(Long userId, String searchValue, int pageNo, int pageSize) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);

        Pageable paging = PageRequest.of(pageNo, pageSize);

        if(searchValue.isEmpty()) return  repository.findAllByPostedByAndActiveIsTrue(user, paging);
        else return repository.findByPostedByAndActiveIsTrueAndNameContains(user, searchValue, paging);
    }

    @Override
    public Page<Listing> getAllSuccessfulOglasiByUser(Long userId, int pageNo, int pageSize, String searchValue) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);

        Pageable paging = PageRequest.of(pageNo, pageSize);

        if(searchValue.isEmpty()) return  repository.findAllByPostedByAndDealIsNotNull(user, paging);
        else return repository.findAllByPostedByAndDealIsNotNullAndNameContains(user, paging, searchValue);
    }

    @Override
    public Page<Listing> getAllUnsuccessfulOglasiByUser(Long userId, int pageNo, int pageSize, String searchValue) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);

        Pageable paging = PageRequest.of(pageNo, pageSize);

        if(searchValue.isEmpty()) return  repository.findAllByPostedByAndActiveIsFalseAndDealIsNull(user, paging);
        else return repository.findAllByPostedByAndActiveIsFalseAndDealIsNullAndNameContains(user, searchValue, paging);
    }


    @Override
    public Listing modifyOglas(Long oglasId, ListingDto listingDto) {
        Listing listing = repository.findById(oglasId).orElseThrow(OglasNotFoundException::new);
        BeanUtils.copyProperties(listingDto, listing, "id", "postedBy");

        return repository.save(listing);
    }


    @Override
    public Listing deleteOglas(Long oglasId) {
        Listing listing = repository.findById(oglasId).orElseThrow(OglasNotFoundException::new);
        repository.delete(listing);
        return listing;
    }

}
