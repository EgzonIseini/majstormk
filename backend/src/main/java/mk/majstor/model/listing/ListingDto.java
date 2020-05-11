package mk.majstor.model.listing;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import mk.majstor.model.enums.City;

import java.time.LocalDateTime;

@Setter
@Getter
public class ListingDto {

    @JsonProperty("name")
    private String name;

    @JsonProperty("postedBy")
    private Long postedBy;

    @JsonProperty("description")
    private String description;

    @JsonProperty("dueDate")
    private LocalDateTime dueDate;

    @JsonProperty("city")
    private City city;

    @JsonProperty("isActive")
    private boolean isActive;

    @JsonProperty("price")
    private Float price;

    @Override
    public String toString() {
        return "ListingDto{" +
                "name='" + name + '\'' +
                ", postedBy=" + postedBy +
                ", description='" + description + '\'' +
                ", dueDate=" + dueDate +
                ", city=" + city +
                ", isActive=" + isActive +
                ", price=" + price +
                '}';
    }

}
