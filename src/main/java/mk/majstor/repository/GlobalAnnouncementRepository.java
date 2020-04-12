package mk.majstor.repository;

import mk.majstor.model.announcement.global.GlobalAnnouncement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GlobalAnnouncementRepository extends JpaRepository<GlobalAnnouncement, Long> {

    List<GlobalAnnouncement> findFirst5ByOrderByIdDesc();

    Page<GlobalAnnouncement> findAllByOrderByIdDesc(Pageable pageable);

}
