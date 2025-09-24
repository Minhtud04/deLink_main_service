package depauw.mnguyen.delink.auth;

import depauw.mnguyen.delink.auth.dto.LoginRequest;
import depauw.mnguyen.delink.auth.dto.RegisterRequest;
import depauw.mnguyen.delink.models.User;
import depauw.mnguyen.delink.user.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthService(PasswordEncoder pwdEnc, AuthenticationManager authManager, UserRepository userRepo) {
        this.passwordEncoder = pwdEnc;
        this.authenticationManager = authManager;
        this.userRepository = userRepo;
    }

    @Transactional
    public User registerNewUser(RegisterRequest request) {

        if (userRepository.findByEmail(request.email()).isPresent()) {
            throw new IllegalStateException("User with email " + request.email() + " already exists.");
        }

        User newUser = new User();
        newUser.setFullName(request.fullName());
        newUser.setEmail(request.email());
        newUser.setRole(request.role());
        newUser.setPasswordHash(passwordEncoder.encode(request.passwordHash()));

        return userRepository.save(newUser);
    }

    public User authenticateUser(LoginRequest request) {
        // 1. Let Spring Security do the authentication
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.passwordHash())
        );

        // 2. If successful, set the authentication in the security context
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // 3. Retrieve the user from the database to return full details
        return userRepository.findByEmail(request.email())
                .orElseThrow(() -> new IllegalStateException("User not found after successful authentication."));
    }

    public boolean isProfileComplete(User user) {
        // Check if the corresponding profile entity exists
        if (user.getStudentProfile() != null || user.getAlumniProfile() != null) {
            return true;
        }
        return false;
    }
}
