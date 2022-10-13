package backend.backend.auth.exception;

import lombok.extern.slf4j.Slf4j;

public class TokenValidFailedException extends RuntimeException{

    public TokenValidFailedException() {
        super("Failed to generate token");
    }

    public TokenValidFailedException(String message){

        super(message);
    }
}
