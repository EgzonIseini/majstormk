package mk.majstor.model.user;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import mk.majstor.model.enums.City;
import mk.majstor.model.listing.Listing;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class PrivateUser extends User {

    @JsonProperty("firstName")
    private String firstName;

    @JsonProperty("lastName")
    private String lastName;

    @JsonProperty("dateOfBirth")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dateOfBirth;

    @JsonIgnore
    @OneToMany(mappedBy = "postedBy")
    private List<Listing> listings;

    public PrivateUser() {
        super();
    }

    @JsonCreator
    public PrivateUser(
            final String firstName,
            final String lastName,
            final LocalDate dateOfBirth,
            @JsonProperty("password") final String password,
            @JsonProperty("emailAddress") final String emailAddress,
            @JsonProperty("phoneNumber") final String phoneNumber,
            @JsonProperty("address") final String address,
            @JsonProperty("city") final City city,
            @JsonProperty("zipCode") final Integer zipCode) {

        super(firstName + ' ' + lastName, password, emailAddress, phoneNumber, address, city, zipCode);
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.listings = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "PrivateUser{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", super=" + super.toString() +
                '}';
    }

    @Override
    public String getName() {
        return firstName + ' ' + lastName.charAt(0) + '.';
    }
}
