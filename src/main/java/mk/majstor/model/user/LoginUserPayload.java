package mk.majstor.model.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class LoginUserPayload {

    private final String username;

    private final String password;

}
