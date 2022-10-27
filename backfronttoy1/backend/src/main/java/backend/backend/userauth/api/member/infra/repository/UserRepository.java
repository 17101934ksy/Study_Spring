package backend.backend.userauth.api.member.infra.repository;

import backend.backend.userauth.api.member.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUserId(String userId);

    User findByEmail(String email);

}
