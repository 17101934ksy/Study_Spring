package capstonv1.capstonv1.api.member.infra;

import capstonv1.capstonv1.api.member.domain.MemberRefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRefreshTokenRepository extends JpaRepository<MemberRefreshToken, Long> {

    MemberRefreshToken findBySocialId(String socialId);
    MemberRefreshToken findBySocialIdAndRefreshToken(String socialId, String refreshToken);
}
