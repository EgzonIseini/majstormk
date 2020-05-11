package mk.majstor.repository;

import mk.majstor.model.listing.Listing;
import mk.majstor.model.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

// This class is a mess. Please ignore it for now. :)
@Repository
public interface ListingRepository extends JpaRepository<Listing, Long> {

    List<Listing> findAllByPostedBy(User postedBy);

    Page<Listing> findAllByNameContains(String name, Pageable pageable);

    Page<Listing> findByPostedByAndNameContains(User user, String name, Pageable pageable);
    Page<Listing> findByPostedByAndActiveIsTrueAndNameContains(User user, String name, Pageable pageable);

    Page<Listing> findByPostedByAndActiveIsFalseAndNameContains(User user, String name, Pageable pageable);

    Page<Listing> findAllByPostedBy(User user, Pageable pageable);
    Page<Listing> findAllByPostedByAndActiveIsTrue(User user, Pageable pageable);

    Page<Listing> findAllByPostedByAndActiveIsFalse(User user, Pageable pageable);

    Page<Listing> findAllByPostedByAndActiveIsFalseAndDealIsNull(User user, Pageable pageable);

    Page<Listing> findAllByPostedByAndActiveIsFalseAndDealIsNullAndNameContains(User user, String name, Pageable pageable);

    Page<Listing> findAllByPostedByAndDealIsNotNull(User user, Pageable pageable);

    Page<Listing> findAllByPostedByAndDealIsNotNullAndNameContains(User user, Pageable pageable, String name);

    Integer countAllByPostedBy(User user);

    Integer countAllByPostedByAndDealNotNull(User user);

    @Modifying
    @Query(value = "update Listing l set l.active=false where l.dueDate < ?1")
    void deactivateOglasiOlderThan(LocalDate date);

}
