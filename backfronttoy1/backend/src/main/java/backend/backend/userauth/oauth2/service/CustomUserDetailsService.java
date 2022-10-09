package backend.backend.userauth.oauth2.service;

import backend.backend.userauth.api.entity.user.User;
import backend.backend.userauth.oauth2.entity.UserPrincipal;
import backend.backend.userauth.oauth2.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUserId(username);
        if (user == null){
            throw new UsernameNotFoundException("유저를 찾을 수 없습니다.");
        }

        return UserPrincipal.create(user);
    }
}
