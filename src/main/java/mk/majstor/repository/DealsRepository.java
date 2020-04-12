package mk.majstor.repository;

import mk.majstor.model.deal.Deal;
import mk.majstor.model.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DealsRepository extends JpaRepository<Deal, Long> {

    Page<Deal> findAllByUser(User user, Pageable pageable);

    Page<Deal> findAllByUserAndListingNameContains(User user, String name, Pageable pageable);

    Optional<Deal> getByListingId(Long id);

}
