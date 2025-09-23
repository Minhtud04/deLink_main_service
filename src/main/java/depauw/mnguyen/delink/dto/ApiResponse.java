package depauw.mnguyen.delink.dto;

public record ApiResponse<T>(
        String status,
        int code,
        T data,
        String message
) {}