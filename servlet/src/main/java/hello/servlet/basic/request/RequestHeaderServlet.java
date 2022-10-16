package hello.servlet.basic.request;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;

@WebServlet(name = "requestHeaderServlet", urlPatterns = "/request-header")
public class RequestHeaderServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        printStartLine(request);
        printHeaders(request);
        printUtilCookies(request);

        System.out.println("request.getRemoteUser() = " + request.getRemoteUser());
        System.out.println("request.getLocalAddr() = " + request.getLocalAddr());
        System.out.println("request.getRemotePort() = " + request.getRemotePort());
    }

    private static void printStartLine(HttpServletRequest request) {
        System.out.println("==== REQUEST-LINE - START ====");
        System.out.println("request.getMethod() = " + request.getMethod());
        System.out.println("request.getProtocol() = " + request.getProtocol());
        System.out.println("request.getScheme() = " + request.getScheme());
        System.out.println("request.getRequestURI() = " + request.getRequestURI());
        System.out.println("request.getRequestURL() = " + request.getRequestURL());
        System.out.println("request.getQueryString() = " + request.getQueryString());
        System.out.println("request.isSecure() = " + request.isSecure());
    }
    
    private void printHeaders(HttpServletRequest request) {

        System.out.println("----------Header Start---------------");

//        Enumeration<String> headerNames = request.getHeaderNames();
//        while (headerNames.hasMoreElements()) {
//            String headerName = headerNames.nextElement();
//            System.out.println("headerName = " + headerName);
//        }

        request.getHeaderNames().asIterator()
                        .forEachRemaining(headerName -> System.out.println("headerName = " + headerName));

        request.getHeader("host");

        System.out.println("----------Header end---------------");
    }

    private void printUtilCookies(HttpServletRequest request) {
        System.out.println("[Cookie 편의 조회]");
        
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                System.out.println("cookie.getName() = " + cookie.getName());
                System.out.println("cookie.getValue() = " + cookie.getValue());
            }
        }

        System.out.println("request.getContentType() = " + request.getContentType());
        System.out.println("request.getContentLength() = " + request.getContentLength());
        System.out.println("request.getCharacterEncoding() = " + request.getCharacterEncoding());
    }
}
