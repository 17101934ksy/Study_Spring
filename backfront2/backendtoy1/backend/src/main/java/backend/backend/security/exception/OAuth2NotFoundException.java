package backend.backend.security.exception;

public class OAuth2NotFoundException extends RuntimeException {
    public OAuth2NotFoundException(String message) {
        super(message);
    }
}
