package mk.majstor.service.impl;

import mk.majstor.repository.UserRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;

    public JwtUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        mk.majstor.model.user.User user = userRepository.findByEmailAddress(username).orElseThrow(() -> {
            throw new UsernameNotFoundException("User not found with username: " + username);
        });

        return new User(user.getEmailAddress(), user.getPassword(), new ArrayList<>());
    }
}