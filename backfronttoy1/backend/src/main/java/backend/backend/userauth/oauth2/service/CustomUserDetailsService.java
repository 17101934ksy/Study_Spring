package backend.backend.userauth.oauth2.service;

import backend.backend.userauth.api.entity.user.User;
import backend.backend.userauth.oauth2.entity.UserPrincipal;
import backend.backend.userauth.api.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUserId(username);

        log.info("CustomUserDetailsService loadUserByUsername");

        if (user == null){
            log.info("CustomUserDetailsService loadUserByUsername user not found");
            throw new UsernameNotFoundException("유저를 찾을 수 없습니다.");
        }

        return UserPrincipal.create(user);
    }
}
