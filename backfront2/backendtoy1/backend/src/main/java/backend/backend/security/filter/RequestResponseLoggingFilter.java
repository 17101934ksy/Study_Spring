package backend.backend.security.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Slf4j
@Order(2)
public class RequestResponseLoggingFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        if (request.getContentType().equals("application/json")){
            log.info("Content-type: application-json!!!!");
        }

        log.info(request.getContentType());
        log.info("Request {} : {}", req.getMethod(), req.getRequestURI());
        log.info("Header : {}", req.getHeader("Authorization"));
    }
}
