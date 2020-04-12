package mk.majstor.model.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserFactoryInvalidArugments extends Exception {
    public UserFactoryInvalidArugments(String argument) {
        super("Argument is: " + argument);
    }
}
