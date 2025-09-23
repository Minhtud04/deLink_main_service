package depauw.mnguyen.delink.dto;

public record ErrorResponse(
        String status,
        int code,
        String message
) {}

