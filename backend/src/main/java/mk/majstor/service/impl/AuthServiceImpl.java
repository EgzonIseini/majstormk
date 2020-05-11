package mk.majstor.service.impl;

import mk.majstor.model.auth.JwtAuthenticationResponse;
import mk.majstor.model.auth.LoginRequest;
import mk.majstor.model.exceptions.EmailAlreadyExists;
import mk.majstor.model.roles.Role;
import mk.majstor.model.roles.RoleName;
import mk.majstor.model.user.CompanyUser;
import mk.majstor.model.user.PrivateUser;
import mk.majstor.model.user.User;
import mk.majstor.repository.RoleRepository;
import mk.majstor.security.JwtTokenProvider;
import mk.majstor.security.UserPrincipal;
import mk.majstor.service.AuthService;
import mk.majstor.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtTokenProvider tokenProvider;

    private final UserService userService;

    public AuthServiceImpl(AuthenticationManager authenticationManager, UserService userService, RoleRepository roleRepository, PasswordEncoder passwordEncoder, JwtTokenProvider tokenProvider) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenProvider = tokenProvider;
    }

    @Override
    public JwtAuthenticationResponse loginUser(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.generateToken(authentication);
        return new JwtAuthenticationResponse(jwt);
    }

    @Override
    public User registerPrivateUser(PrivateUser user) {
        // Check if account exists.
        if(userService.doesEmailAddressExist(user.getEmailAddress())) throw new EmailAlreadyExists();

        // Creating user's account
        String password = passwordEncoder.encode(user.getPassword());
        user.setPassword(password);

        Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
                .orElseThrow(() -> new RuntimeException("User Role not set."));

        user.setRoles(Collections.singleton(userRole));

        return userService.saveUser(user);
    }

    @Override
    public User registerCompanyUser(CompanyUser user) {
        // Creating user's account
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
                .orElseThrow(() -> new RuntimeException("User Role not set."));

        user.setRoles(Collections.singleton(userRole));

        return userService.saveUser(user);
    }

    @Override
    public User changeUserPassword(UserPrincipal userPrincipal, String newPassword) {
        User user = userService.getUserById(userPrincipal.getId());
        user.setPassword(passwordEncoder.encode(newPassword));
        return userService.saveUser(user);
    }

    @Override
    public Boolean checkPassword(UserPrincipal user, String password) {
        return (passwordEncoder.matches(password, user.getPassword()));
    }
}
