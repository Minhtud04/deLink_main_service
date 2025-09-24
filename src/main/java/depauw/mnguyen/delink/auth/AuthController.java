package depauw.mnguyen.delink.auth;


import depauw.mnguyen.delink.auth.dto.*;
import depauw.mnguyen.delink.dto.ApiResponse;
import depauw.mnguyen.delink.models.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.jspecify.annotations.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private final Logger log = LoggerFactory.getLogger(AuthController.class);;
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<@NonNull ApiResponse<AuthResponse>> register(@Valid @RequestBody RegisterRequest request) {
        log.info("auth controller reached");
        User newUser = authService.registerNewUser(request);

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
