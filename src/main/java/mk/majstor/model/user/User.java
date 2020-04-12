package mk.majstor.model.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import mk.majstor.model.enums.City;
import mk.majstor.model.roles.Role;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class User {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;

    @JsonIgnore
    private String name;

    @JsonIgnore
    private String password;

    private String emailAddress;

    private String phoneNumber;

    private String address;

    private City city;

    private Integer zipCode;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY)
    private List<User> following;

    @JsonIgnore
    @ManyToMany(mappedBy = "following")
    private List<User> followers;

    @JsonProperty("roles")
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    public User() { }

    public User(String name, String password, String emailAddress, String phoneNumber, String address, City city, Integer zipCode) {
        this.name = name;
        this.password = password;
        this.emailAddress = emailAddress;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.city = city;
        this.zipCode = zipCode;
        this.following = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", emailAddress='" + emailAddress + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", address='" + address + '\'' +
                ", city=" + city +
                ", zipCode=" + zipCode +
                '}';
    }
}
