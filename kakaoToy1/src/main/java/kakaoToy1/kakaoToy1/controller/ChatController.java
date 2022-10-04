package kakaoToy1.kakaoToy1.controller;

import kakaoToy1.kakaoToy1.service.TestSpaceStatusService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


@Controller
@Log4j2
public class ChatController {

    private final TestSpaceStatusService testSpaceStatusService;

    @Autowired
    public ChatController(TestSpaceStatusService testSpaceStatusService) {
        this.testSpaceStatusService = testSpaceStatusService;
    }

    @GetMapping("/chat")
    public void getChat(HttpServletRequest request){

        HttpSession session = request.getSession();
    }

    @GetMapping("/chat/master")
    public String enterChatAsMaster(HttpServletRequest request){

        log.info("@ChatController, getChat()");
        return "/chat";
    }
}