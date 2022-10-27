package backend.backend.userauth.oauth2.application.service;

import backend.backend.userauth.api.member.domain.User;
import backend.backend.userauth.oauth2.domain.UserPrincipal;
import backend.backend.userauth.api.member.infra.repository.UserRepository;
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
