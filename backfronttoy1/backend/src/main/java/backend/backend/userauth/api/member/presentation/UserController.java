package backend.backend.userauth.api.member.presentation;

import backend.backend.userauth.api.member.application.UserService;
import backend.backend.userauth.api.member.domain.User;
import backend.backend.userauth.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    public ApiResponse getUser() {

        log.info("auth1");

        org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User) SecurityContextHolder
                .getContext().getAuthentication().getPrincipal();


        log.info("auth2");

        User user = userService.getUser(principal.getUsername());


        log.info("auth3");

        return ApiResponse.success("user", user);

    }
}


