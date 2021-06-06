package by.bsu.d0mpi.UP_PostGallery.servlet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "error", value = "/error")
public class ErrorServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Throwable throwable = (Throwable)
                req.getAttribute("javax.servlet.error.exception");
        Integer statusCode = (Integer)
                req.getAttribute("javax.servlet.error.status_code");
        String servletName = (String)
                req.getAttribute("javax.servlet.error.servlet_name");
        req.setAttribute("status_code",statusCode == null ? -1 : statusCode);
        req.setAttribute("throwable", throwable);
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/WEB-INF/views/error.jsp");
        requestDispatcher.forward(req,resp);
    }
}
