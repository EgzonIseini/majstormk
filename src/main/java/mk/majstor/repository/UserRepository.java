package mk.majstor.repository;

import mk.majstor.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmailAddress(String email);

    List<User> findUsersByFollowing(User user);

    List<User> findUsersByFollowers(User user);

    Boolean existsByEmailAddress(String emailAddress);

}
