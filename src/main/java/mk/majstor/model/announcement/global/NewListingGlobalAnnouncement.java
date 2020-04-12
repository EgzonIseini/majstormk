package mk.majstor.model.announcement.global;

import lombok.NoArgsConstructor;
import mk.majstor.model.announcement.AnnouncementType;
import mk.majstor.model.listing.Listing;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@NoArgsConstructor
@DiscriminatorValue("new_listing")
public class NewListingGlobalAnnouncement extends GlobalAnnouncement {

    private NewListingGlobalAnnouncement(String announcement, Long entityId) {
        super(announcement, entityId, AnnouncementType.GLOBAL_NEW_LISTING);
    }

    public static NewListingGlobalAnnouncement of(Listing listing) {
        String userName = listing.getPostedBy().getName();
        String listingName = listing.getName();
        String cityName = listing.getCity().getValue();

        return new NewListingGlobalAnnouncement(String.format("<strong>%s</strong> објави оглас за општина <strong>%s</strong> со наслов: <strong>%s</strong>", userName, cityName, listingName), listing.getId());
    }

}
