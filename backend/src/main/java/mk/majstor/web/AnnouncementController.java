package mk.majstor.web;

import mk.majstor.model.announcement.Announcement;
import mk.majstor.model.announcement.global.GlobalAnnouncement;
import mk.majstor.model.listing.Listing;
import mk.majstor.repository.GlobalAnnouncementRepository;
import mk.majstor.security.CurrentUser;
import mk.majstor.security.UserPrincipal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping(path = "/announcements", produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
public class AnnouncementController {

    private final GlobalAnnouncementRepository globalAnnouncementRepository;

    public AnnouncementController(
            GlobalAnnouncementRepository globalAnnouncementRepository
    ) {
        this.globalAnnouncementRepository = globalAnnouncementRepository;
    }

    @GetMapping("/global/latest")
    public List<GlobalAnnouncement> getLatestGlobalAnnouncements()
    {
        return globalAnnouncementRepository.findFirst5ByOrderByIdDesc();
    }

    @GetMapping(path = "/global")
    public Page<GlobalAnnouncement> getAllGlobalAnnouncements(
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize
    ) {
        Pageable paging = PageRequest.of(pageNo, pageSize);

        return globalAnnouncementRepository.findAllByOrderByIdDesc(paging);
    }
}
