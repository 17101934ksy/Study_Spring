package backend.backend.service;

import backend.backend.dto.LoginDTO;
import backend.backend.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
@Configuration
public class UserService {

    private final UserRepository userRepository;

    public String loginAccount(String loginId, String loginPw){
        if (loginId.equals("test")&&loginPw.equals("test")){
            return "login ok";
        } else {
            return "login forbidden";
        }
    }
}
