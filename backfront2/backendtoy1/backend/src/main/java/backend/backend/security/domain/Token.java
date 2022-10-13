package backend.backend.security.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.TimeToLive;

import javax.persistence.Id;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.TimeUnit.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public abstract class Token {

    @Id
    private String id;

    @TimeToLive(unit = MILLISECONDS)
    private long expiration;
}
