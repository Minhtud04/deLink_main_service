package depauw.mnguyen.delink.auth.dto;

import depauw.mnguyen.delink.models.enums.UserRole;

import java.util.UUID;

public record AuthResponse(
        UUID userId,
        String fullName,
        UserRole role,
        boolean profileComplete
){}