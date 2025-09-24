package depauw.mnguyen.delink.auth;

import depauw.mnguyen.delink.models.User;
import depauw.mnguyen.delink.user.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import java.util.ArrayList;

@Service // This annotation makes it a Spring bean, so it can be auto-detected.
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * This method is called by Spring Security's AuthenticationManager.
     * It's the bridge between the security framework and our user data model.
     * @param email The username provided during the login attempt (in our case, the email).
     * @return A UserDetails object that Spring Security can use to verify the password.
     * @throws UsernameNotFoundException if no user is found with the given email.
     */
    @Override
    @Transactional // Good practice to ensure the user object is fully loaded
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // 1. Find our custom User entity from the database using the repository.
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        // 2. Convert our User entity into a standard Spring Security UserDetails object.
        // The framework will handle the password comparison automatically using this object.
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),          // The username
                user.getPasswordHash(),   // The HASHED password from the database
                new ArrayList<>()         // A list of authorities/roles (empty for now)
        );
    }
}