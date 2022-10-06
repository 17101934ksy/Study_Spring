package springlogintest.springlogintest.Home.presentation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController("/api")
public class HomeController {

    @GetMapping()
    public String index() {
        return "hello";
    }

    @GetMapping("/request-param-required")
    public String requestParamRequired(@RequestParam String username,
                                       @RequestParam(required = false) int age){
        log.info("username = {}, age = {}", username, age);
        return "username = " + username + " age = " + age;
    }
}


