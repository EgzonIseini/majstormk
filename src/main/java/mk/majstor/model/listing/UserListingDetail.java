package mk.majstor.model.listing;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserListingDetail {

    private Integer totalListings;

    private Integer successfulListings;

    private Integer totalOffers;

    private Float moneySaved;

}
