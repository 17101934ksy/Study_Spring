package backend.backend.userauth.api.service;

import backend.backend.userauth.api.entity.user.User;
import backend.backend.userauth.oauth2.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User getUser(String userId) {
        return userRepository.findByUserId(userId);
    }
}
