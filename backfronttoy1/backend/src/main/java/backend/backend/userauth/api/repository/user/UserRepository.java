package backend.backend.userauth.api.repository.user;

import backend.backend.userauth.api.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUserId(String userId);

    User findByEmail(String email);

}
