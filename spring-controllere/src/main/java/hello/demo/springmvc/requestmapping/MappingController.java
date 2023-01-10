package hello.demo.springmvc.requestmapping;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
public class MappingController {

    @GetMapping("/hello-basic")
    public String helloBasic() {
        log.info("hello basic");
        return "ok";
    }

    @PostMapping("/hello-basic")
    public String helloPostBasic() {
        log.info("hello basic");
        return "ok";
    }

    // 템플릿화
    @GetMapping("/mapping/{userId}")
    public String mappingPath(@PathVariable("userId") String userId) {
        log.info("userId = {}", userId);
        return "ok";
    }

    // pathVariable 생략, @PathVariable 자체를 생략하면 안된다.
//    @GetMapping("/mapping/{userId}")
//    public String mappingPath2(@PathVariable String userId) {
//        log.info("userId = {}", userId);
//        return "ok";
//    }

    @GetMapping("/mapping/users/{userId}/orders/{orderId}")
    public String mappingPath(@PathVariable String userId, @PathVariable Long orderId) {
        log.info("mappingPath userId = {}, orderId = {}", userId, orderId);
        return "ok";
    }

    @PostMapping(value = "/mapping-consume", consumes = MediaType.APPLICATION_JSON_VALUE)
    public String mappingConsume() {
        log.info("consume");
        return "ok";
    }

    @PostMapping(value = "/mapping-produce", produces = MediaType.APPLICATION_JSON_VALUE)
    public String mappingProduces() {
        log.info("mapping produces");
        return "ok";
    }


}
