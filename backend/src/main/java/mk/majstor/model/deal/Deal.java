package mk.majstor.model.deal;

import lombok.Data;
import lombok.NoArgsConstructor;
import mk.majstor.model.listing.Listing;
import mk.majstor.model.user.PrivateUser;
import mk.majstor.model.user.User;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.sql.Timestamp;

@NoArgsConstructor
@Entity
@Data
@Where(clause = "listing_id IS NOT NULL")
public class Deal {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    private PrivateUser user;

    @ManyToOne
    private User company;

    @OneToOne
    private Listing listing;

    private Timestamp date;

    private Float price;

    public Deal(PrivateUser user, User company, Listing listing, Float agreedPrice, Timestamp date) {
        this.user = user;
        this.company = company;
        this.listing = listing;
        this.date = date;
        this.price = agreedPrice;
    }

    @Override
    public String toString() {
        return "Deal{" +
                "user=" + user +
                "madeBy=" + company +
                "price=" + price +
                ", listing=" + listing +
                '}';
    }
}
