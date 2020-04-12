package mk.majstor.web;

import mk.majstor.model.auth.JwtAuthenticationResponse;
import mk.majstor.model.auth.LoginRequest;
import mk.majstor.model.exceptions.EmailAlreadyExists;
import mk.majstor.model.roles.RoleName;
import mk.majstor.model.roles.Role;
import mk.majstor.model.user.*;
import mk.majstor.repository.RoleRepository;
import mk.majstor.security.CurrentUser;
import mk.majstor.security.JwtTokenProvider;
import mk.majstor.security.UserPrincipal;
import mk.majstor.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Collections;

@RestController
@RequestMapping("/users/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;

    private final UserService userService;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtTokenProvider tokenProvider;

    public AuthController(AuthenticationManager authenticationManager, UserService userService, RoleRepository roleRepository, PasswordEncoder passwordEncoder, JwtTokenProvider tokenProvider) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenProvider = tokenProvider;
    }


    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginUserPayload) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginUserPayload.getUsername(),
                        loginUserPayload.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
    }

    @PostMapping(path = "/register/private")
    public ResponseEntity<User> addPrivateUser(@Valid @RequestBody PrivateUser user) {

        // Check if account exists.
        if(userService.doesEmailAddressExist(user.getEmailAddress())) throw new EmailAlreadyExists();

        // Creating user's account
        String password = passwordEncoder.encode(user.getPassword());
        user.setPassword(password);

        Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
                .orElseThrow(() -> new RuntimeException("User Role not set."));

        user.setRoles(Collections.singleton(userRole));

        User createdUser = userService.saveUser(user);

/*
        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/api/users/{username}")
                .buildAndExpand(result.getEmailAddress()).toUri();
*/

        //return ResponseEntity.created(location).body(user);

        return ResponseEntity.ok(createdUser);
    }

    @PostMapping(path = "/register/company")
    public ResponseEntity<User> addCompanyUser(@Valid @RequestBody CompanyUser user) {

        // Creating user's account
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
                .orElseThrow(() -> new RuntimeException("User Role not set."));

        user.setRoles(Collections.singleton(userRole));
        User createdUser = userService.saveUser(user);

        return ResponseEntity.ok(createdUser);
    }

    @PostMapping(path = "/check-password")
    public ResponseEntity<?> checkPassword(@CurrentUser UserPrincipal user, @RequestBody String password) {
        if(passwordEncoder.matches(password, user.getPassword())) {
            return ResponseEntity.ok().build();
        }
        else return ResponseEntity.badRequest().build();
    }

    @PostMapping(path = "/change-password")
    public User changePassword(@CurrentUser UserPrincipal currentUser, @RequestBody String password) {
        User user = userService.getUserById(currentUser.getId());
        user.setPassword(passwordEncoder.encode(password));
        return userService.saveUser(user);
    }
}