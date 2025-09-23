package depauw.mnguyen.delink.auth;

import depauw.mnguyen.delink.auth.dto.RegisterRequest;
import depauw.mnguyen.delink.models.User;
import jakarta.transaction.Transactional;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

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
        newUser.setPasswordHash(passwordEncoder.encode(request.password()));

        return userRepository.save(newUser);
    }


}
