package mk.majstor.service;

import mk.majstor.model.listing.UserListingDetail;
import mk.majstor.model.user.User;

import java.util.List;

public interface UserService {

    List<User> getAllUsers(int pageNo, int pageSize, String sortBy);

    User getUserById(long id);

    User getUserByEmail(String email);

    User saveUser(User user);

    User deleteUser(User user);

    User modifyUser(long id, User newUser);

    User followUser(long followerId, long toFollowId);

    List<User> getUserFollowers(long userId);

    List<User> getUserFollowing(long userId);

    Boolean doesEmailAddressExist(String email);

    UserListingDetail getUserListingDetails(Long userId);

}
