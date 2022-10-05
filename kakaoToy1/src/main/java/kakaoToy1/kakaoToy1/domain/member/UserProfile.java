package kakaoToy1.kakaoToy1.domain.member;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class UserProfile {

    @Id @GeneratedValue
    private Long id;
}
