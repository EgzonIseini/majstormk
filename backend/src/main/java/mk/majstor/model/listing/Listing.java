package mk.majstor.model.listing;

import com.fasterxml.jackson.annotation.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import mk.majstor.model.offer.Offer;
import mk.majstor.model.deal.Deal;
import mk.majstor.model.enums.City;
import mk.majstor.model.user.User;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Listing {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;

    private String name;

    @ManyToOne
    @JsonIdentityReference(alwaysAsId = true)
    private User postedBy;

    private String description;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime dueDate;

    @JsonIgnore
    @OneToMany(mappedBy = "listing")
    private List<Offer> deals;

    private City city;

    private boolean active;

    private boolean deletedFlag;

    @JsonIgnore
    @OneToOne
    private Deal deal;

    private Float price;

    public Listing(
            @JsonProperty("name") String name,
            @JsonProperty("userId")User postedBy,
            @JsonProperty("description") String description,
            @JsonProperty("dueDate") LocalDateTime dueDate,
            @JsonProperty("city") City city,
            @JsonProperty("isActive") Boolean isActive,
            @JsonProperty("price") Float price)
    {
        this.name = name;
        this.postedBy = postedBy;
        this.description = description;
        this.dueDate = dueDate;
        this.city = city;
        this.active = isActive;
        this.price = price;
        this.deal = null;
        this.deals = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "Listing{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", postedBy=" + postedBy +
                ", description='" + description + '\'' +
                ", dueDate=" + dueDate +
                ", city=" + city +
                ", isActive=" + active +
                ", deletedFlag=" + deletedFlag +
                ", price=" + price +
                '}';
    }
}
