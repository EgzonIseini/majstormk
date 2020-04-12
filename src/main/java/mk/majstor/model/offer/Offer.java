package mk.majstor.model.offer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mk.majstor.model.listing.Listing;
import mk.majstor.model.user.User;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
@Where(clause = "listing_id IS NOT NULL")
public class Offer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    private User madeBy;

    private String description;

    private Boolean wasAccepted;

    @ManyToOne
    private Listing listing;

    private Float offeredPrice;

}
