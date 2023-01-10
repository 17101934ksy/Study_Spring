package hello.demo.springmvc.requestmapping;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/mapping/class/users")
public class MappingClassController {

    @GetMapping
    public String user() {
        log.info("/users");
        return "get users";
    }

    @PostMapping
    public String addUser() {
        log.info("/users");
        return "post user";
    }

    @GetMapping("/{userId}")
    public String findUser(@PathVariable String userId) {
        log.info("/users/{}", userId);
        return "Get userId = " + userId;
    }

    @PatchMapping("/{userId}")
    public String updateUser(@PathVariable String userId) {
        log.info("/users/{}", userId);
        return "Patch userId = " + userId;
    }

    @DeleteMapping("/{userId}")
    public String deleteUser(@PathVariable String userId) {
        log.info("/users/{}", userId);
        return "Delete userId = " + userId;
    }

}
