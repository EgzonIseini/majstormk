package mk.majstor.web;

import mk.majstor.model.auth.LoginRequest;
import mk.majstor.model.user.CompanyUser;
import mk.majstor.model.user.PrivateUser;
import mk.majstor.model.user.User;
import mk.majstor.security.CurrentUser;
import mk.majstor.security.UserPrincipal;
import mk.majstor.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/users/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }


    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginUserPayload) {
        return ResponseEntity.ok(authService.loginUser(loginUserPayload));
    }

    @PostMapping(path = "/register/private")
    public ResponseEntity<User> addPrivateUser(@Valid @RequestBody PrivateUser user) {
        return ResponseEntity.ok(authService.registerPrivateUser(user));
    }

    @PostMapping(path = "/register/company")
    public ResponseEntity<User> addCompanyUser(@Valid @RequestBody CompanyUser user) {
        return ResponseEntity.ok(authService.registerCompanyUser(user));
    }

    @PostMapping(path = "/check-password")
    public ResponseEntity<?> checkPassword(@CurrentUser UserPrincipal user, @RequestBody String password) {
        if(authService.checkPassword(user, password)) {
            return ResponseEntity.ok().build();
        }
        else return ResponseEntity.badRequest().build();
    }

    @PostMapping(path = "/change-password")
    public User changePassword(@CurrentUser UserPrincipal currentUser, @RequestBody String password) {
        return authService.changeUserPassword(currentUser, password);
    }
}
