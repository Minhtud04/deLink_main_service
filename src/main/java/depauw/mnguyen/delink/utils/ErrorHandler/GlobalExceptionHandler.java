package depauw.mnguyen.delink.utils.ErrorHandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import depauw.mnguyen.delink.dto.ErrorResponse;
import java.util.HashMap;
import java.util.Map;

// This annotation makes this class a global exception handler for all @RestControllers
@RestControllerAdvice
public class GlobalExceptionHandler {
    // This handler catches the exception we throw when a user tries to register with an existing email
    @ExceptionHandler(ErrorException.ResourceAlreadyExists.class)
    public ResponseEntity<ErrorResponse> handleUserAlreadyExists(ErrorException.ResourceAlreadyExists ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                "ERROR",
                HttpStatus.CONFLICT.value(), // 409
                ex.getMessage()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }


    // A generic handler for "not found" errors
    @ExceptionHandler(ErrorException.ResourceNotFound.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFound(ErrorException.ResourceNotFound ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                "ERROR",
                HttpStatus.NOT_FOUND.value(), // 404
                ex.getMessage()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(ErrorException.ResourceNotFound.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFound(ErrorException.Unauthorization ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                "ERROR",
                HttpStatus.NOT_FOUND.value(), // 404
                ex.getMessage()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }


    // This handler catches validation errors from DTOs (e.g., @NotBlank, @Email)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage()));

        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("status", "ERROR");
        responseBody.put("code", HttpStatus.BAD_REQUEST.value()); // 400
        responseBody.put("message", "Validation failed");
        responseBody.put("errors", errors);

        return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
    }





    // A catch-all handler for any other unexpected exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
        // Log the exception for debugging purposes
        ex.printStackTrace(); // In a real app, you'd use a proper logger

        ErrorResponse errorResponse = new ErrorResponse(
                "ERROR",
                HttpStatus.INTERNAL_SERVER_ERROR.value(), // 500
                "An unexpected internal server error occurred."
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}