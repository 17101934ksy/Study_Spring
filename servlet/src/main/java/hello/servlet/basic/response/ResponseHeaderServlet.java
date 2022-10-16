package hello.servlet.basic.response;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "responseHeaderServlet", urlPatterns = "/response-header")
public class ResponseHeaderServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        content(response);

//        cookie(response);

        redirect(response);

        PrintWriter printWriter = response.getWriter();
        printWriter.println("ok");

    }

    private void content(HttpServletResponse response) {
        //[status-line]
        response.setStatus(HttpServletResponse.SC_OK);

        //[response-header]
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("my-header", "hello");

        response.setContentType("test/plain");
        response.setCharacterEncoding("utf-8");
    }

    private void cookie(HttpServletResponse response) {

        Cookie cookie = new Cookie("myCookie", "good");
        cookie.setMaxAge(600);

        response.addCookie(cookie);
    }

    private void redirect(HttpServletResponse response) throws IOException {

//        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
//        response.setHeader("Location", "/basic/hello-form.html");

        response.sendRedirect("/basic/hello-form.html");
    }

}
