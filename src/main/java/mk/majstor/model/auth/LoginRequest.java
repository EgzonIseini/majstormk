package mk.majstor.model.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@Getter
public class LoginRequest {

    @NotBlank
    private String username;

    @NotBlank
    private String password;

}