package depauw.mnguyen.delink.auth.dto;

import depauw.mnguyen.delink.models.enums.UserRole;
import jakarta.validation.constraints.*;

public record RegisterRequest(
        @NotBlank(message = "Full name cannot be blank")
        String fullName,

        @NotBlank(message = "Email cannot be blank")
        @Email(message = "Email should be valid")
//        @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@depauw\\.edu$", message = "Email must be a valid @depauw.edu address")
        String email,

        @NotBlank(message = "Password cannot be blank")                 // Hash before sending to backend, and hash the second time at backend
        String passwordHash,

        @NotNull(message = "Role must be specified")
        UserRole role
) {}