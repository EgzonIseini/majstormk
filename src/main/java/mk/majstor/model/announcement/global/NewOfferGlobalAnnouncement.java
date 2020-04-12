package mk.majstor.model.announcement.global;

import lombok.NoArgsConstructor;
import mk.majstor.model.announcement.AnnouncementType;
import mk.majstor.model.listing.Listing;
import mk.majstor.model.offer.Offer;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@NoArgsConstructor
@DiscriminatorValue("new_offer")
public class NewOfferGlobalAnnouncement extends GlobalAnnouncement {

    private NewOfferGlobalAnnouncement(String announcement, Long entityId) {
        super(announcement, entityId, AnnouncementType.GLOBAL_NEW_OFFER);
    }

    public static NewOfferGlobalAnnouncement of(Offer offer, Listing listing) {
        String userName = offer.getMadeBy().getName();
        String listingName = listing.getName();

        return new NewOfferGlobalAnnouncement(String.format("<strong>%s</strong> објави нова понуда за огласот: <strong>%s</strong>", userName, listingName), listing.getId());
    }

}
