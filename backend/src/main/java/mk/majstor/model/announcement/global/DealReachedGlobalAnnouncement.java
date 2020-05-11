package mk.majstor.model.announcement.global;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mk.majstor.model.announcement.AnnouncementType;
import mk.majstor.model.listing.Listing;
import mk.majstor.model.user.CompanyUser;
import mk.majstor.model.user.PrivateUser;
import mk.majstor.model.user.User;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@NoArgsConstructor
@DiscriminatorValue("deal_reached")
public class DealReachedGlobalAnnouncement extends GlobalAnnouncement {

    private DealReachedGlobalAnnouncement(String announcement, Long listingId) {
       super(announcement, listingId, AnnouncementType.GLOBAL_DEAL_REACHED);
    }

    public static DealReachedGlobalAnnouncement of(Listing listing, User companyUser) {
        String customerName = listing.getPostedBy().getName();
        String listingName = listing.getName();
        String companyName = companyUser.getName();

        return new DealReachedGlobalAnnouncement(String.format("<strong>%s</strong> и <strong>%s</strong> постигнаа договор за оглас: <strong>%s</strong>", customerName, companyName, listingName), listing.getId());
    }

}
