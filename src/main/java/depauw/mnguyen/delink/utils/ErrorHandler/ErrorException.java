package depauw.mnguyen.delink.utils.ErrorHandler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class ErrorException {
    /**
     * HTTP 404 Not Found.
     */
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public static class ResourceNotFound extends RuntimeException {
        public ResourceNotFound(String message) {
            super(message);
        }
    }

    /**
     *  Resource already exists.
     *  HTTP 409 Conflict.
     */
    @ResponseStatus(HttpStatus.CONFLICT)
    public static class ResourceAlreadyExists extends RuntimeException {
        public ResourceAlreadyExists(String message) {
            super(message);
        }
    }

    /**
     * Unauthorized: 403
     */
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public static class Unauthorization extends RuntimeException {
        public Unauthorization(String message) {
            super(message);
        }
    }
}