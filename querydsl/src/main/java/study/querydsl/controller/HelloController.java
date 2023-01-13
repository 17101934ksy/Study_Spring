package study.querydsl.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.OK;

@RestController
public class HelloController {

    @GetMapping("/")
    public ResponseEntity hello() {
        return new ResponseEntity("ok", OK);
    }

}
