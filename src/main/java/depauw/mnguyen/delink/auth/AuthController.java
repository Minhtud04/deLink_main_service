package depauw.mnguyen.delink.auth;


import depauw.mnguyen.delink.auth.dto.*;
import depauw.mnguyen.delink.dto.ApiResponse;
import depauw.mnguyen.delink.models.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<AuthResponse>> register(@Valid @RequestBody RegisterRequest request) {
        User newUser = authService.registerNewUser(request);

        authService.authenticateUser(new LoginRequest(request.email(), request.passwordHash()));

        ApiResponse response = new ApiResponse(
                "OK",
                HttpStatus.CREATED.value(),
                new AuthResponse(newUser.getId(),
                        newUser.getFullName(),
                        newUser.getRole(),
                        false // Profile is never complete on initial registration
                ),
                "Registration successful. Please complete your profile."
        );

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(@Valid @RequestBody LoginRequest request) {
        User user = authService.authenticateUser(request);

        boolean profileComplete = authService.isProfileComplete(user);
        String message = profileComplete ? "Login successful." : "Login successful. Please complete your profile.";
        ApiResponse response = new ApiResponse(
                "OK",
                HttpStatus.OK.value(),
                new AuthResponse(
                        user.getId(),
                        user.getFullName(),
                        user.getRole(),
                        profileComplete
                ),
                message
        );

        return ResponseEntity.ok(response);
    }


    //delete session in Redis
    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<Void>> logout(HttpServletRequest request) {
        // Invalidate the session
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        // Clear the security context
        SecurityContextHolder.clearContext();

        ApiResponse<Void> response = new ApiResponse<>(
                "OK",
                HttpStatus.OK.value(),
                null,
                "Logout successful."
        );
        return ResponseEntity.ok(response);
    }

}
