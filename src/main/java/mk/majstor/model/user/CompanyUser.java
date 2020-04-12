package mk.majstor.model.user;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import mk.majstor.model.deal.Deal;
import mk.majstor.model.enums.City;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class CompanyUser extends User {

    private String companyName;

    private String description;

    @JsonIgnore
    @OneToMany(mappedBy = "company")
    private List<Deal> deals;

    public CompanyUser() { super(); }

    @JsonCreator
    public CompanyUser(
            @JsonProperty("name") final String name,
            @JsonProperty("password") final String password,
            @JsonProperty("emailAddress") final String emailAddress,
            @JsonProperty("phoneNumber") final String phoneNumber,
            @JsonProperty("address") final String address,
            @JsonProperty("description") final String description,
            @JsonProperty("city") final City city,
            @JsonProperty("zipCode") final Integer zipCode) {
        super(name, password, emailAddress, phoneNumber, address, city, zipCode);
        this.companyName = name;
        this.description = description;
        this.deals = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "CompanyUser{" +
                "companyName='" + companyName + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
