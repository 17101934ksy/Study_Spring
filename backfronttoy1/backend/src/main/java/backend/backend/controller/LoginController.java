package backend.backend.controller;

import backend.backend.dto.LoginDTO;
import backend.backend.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class LoginController {

    final UserService userService;

    @PostMapping("/login")
    public String loginTest(@RequestBody String data){
        System.out.println("data = " + data);
        return "ok";
    }

    @GetMapping("/example-v1")
    public String getTest(){
        return "get Success!!!!";
    }

    @PostMapping("/example-v1/post")
    public String PostTest(@RequestBody String msg) {
        System.out.println(msg);
        return "post success!!!"+msg;
    }
}
