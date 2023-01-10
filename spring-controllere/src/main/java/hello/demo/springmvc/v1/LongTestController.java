package hello.demo.springmvc.v1;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class LongTestController {

//    private final Logger log = LoggerFactory.getLogger(getClass());

    @RequestMapping("/log-test")
    public String logTest() {
        String name = "Spring";

        log.trace("trace log = {}", name);
        log.debug("debug log = {}", name);
        log.info("info log = {}", name);
        log.warn("warn log = {}", name);
        log.error("error log = {}", name);

        log.debug("String concat log = {}", name);
        return "ok";
    }

    @RequestMapping("/log-test2")
    public String logTest2() {
        String name = "spring";


        log.trace("trace log -> {}", name);
        log.info("name = {}", name);

        return "no";
    }

}
