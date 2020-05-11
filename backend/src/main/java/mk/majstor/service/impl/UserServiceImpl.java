package mk.majstor.service.impl;

import mk.majstor.model.exceptions.UserNotFoundException;
import mk.majstor.model.listing.Listing;
import mk.majstor.model.listing.UserListingDetail;
import mk.majstor.model.user.User;
import mk.majstor.repository.ListingRepository;
import mk.majstor.repository.UserRepository;
import mk.majstor.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final ListingRepository listingRepository;

    public UserServiceImpl(
            UserRepository userRepository,
            ListingRepository listingRepository) {
        this.userRepository = userRepository;
        this.listingRepository = listingRepository;
    }

    @Override
    public List<User> getAllUsers(int pageNo, int pageSize, String sortBy) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Page<User> pagedResult = userRepository.findAll(paging);

        if(pagedResult.hasContent()) return pagedResult.getContent();
        else return new ArrayList<>();
    }

    @Override
    public User getUserById(long id) {
        return userRepository.findById(id).orElseThrow(UserNotFoundException::new);
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmailAddress(email).orElseThrow(UserNotFoundException::new);
    }

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User deleteUser(User user) {
        // To be implemented during the end... :)
        return null;
    }

    @Override
    public User modifyUser(long id, User newUser) {
        User toModify = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        BeanUtils.copyProperties(newUser, toModify, "id");
        return userRepository.save(toModify);
    }

    @Override
    public User followUser(long followerId, long toFollowId) {
        User from = userRepository.findById(followerId).orElseThrow(UserNotFoundException::new);
        User to = userRepository.findById(toFollowId).orElseThrow(UserNotFoundException::new);

        from.getFollowing().add(to);
        userRepository.saveAndFlush(from);

        return from;
    }

    @Override
    public List<User> getUserFollowers(long userId) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);

        return userRepository.findUsersByFollowing(user);
    }

    @Override
    public List<User> getUserFollowing(long userId) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);

        return userRepository.findUsersByFollowers(user);
    }

    @Override
    public Boolean doesEmailAddressExist(String email) {
        return userRepository.existsByEmailAddress(email);
    }

    @Override
    public UserListingDetail getUserListingDetails(Long userId) {

        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        List<Listing> allOglasi = listingRepository.findAllByPostedBy(user);

        int totalOglasi = allOglasi.size(),
                successfulOglasi = 0;

        for(Listing listing : allOglasi) {
            if(!listing.isActive() && listing.getDeal() != null) {
                successfulOglasi++;
            }
        }

        return new UserListingDetail(totalOglasi, successfulOglasi, 0, 0F);
    }

}
