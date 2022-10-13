package backend.backend.security.exception;

public class OAuth2RegisterException extends RuntimeException{
    public OAuth2RegisterException(String message){
        super(message);
    }
}
