package springlogintest.springlogintest.error;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import static javax.servlet.RequestDispatcher.ERROR_STATUS_CODE;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Controller
public class ErrorHandler implements ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request) {

        Object status = request.getAttribute(ERROR_STATUS_CODE);

        if(null != status) {

            int statusCode = Integer.valueOf(status.toString());

            if (statusCode == FORBIDDEN.value()){
                return "error_403";

            } else if(statusCode == NOT_FOUND.value()){
                return "error_404";
            }
        }
        return "error";
    }
}
