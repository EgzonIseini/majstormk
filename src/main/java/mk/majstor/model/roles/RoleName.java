package mk.majstor.model.roles;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public enum RoleName {

    @JsonProperty("user")
    ROLE_USER,

    @JsonProperty("admin")
    ROLE_ADMIN;

}
