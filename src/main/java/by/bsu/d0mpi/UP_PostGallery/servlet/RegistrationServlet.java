package by.bsu.d0mpi.UP_PostGallery.servlet;

import by.bsu.d0mpi.UP_PostGallery.dao.impl.MySqlUserDao;
import by.bsu.d0mpi.UP_PostGallery.model.Role;
import by.bsu.d0mpi.UP_PostGallery.model.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

@WebServlet(name = "registration", value = "/registration")
public class RegistrationServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/WEB-INF/views/register.jsp");
        requestDispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String login = req.getParameter("username");
        String password = req.getParameter("password");
        resp.setContentType("text/plain");
        resp.setCharacterEncoding("UTF-8");
        OutputStream outStream = resp.getOutputStream();

        String respType;
        MySqlUserDao userDao = MySqlUserDao.getInstance();
        User user;

        if (userDao.isLoginPresented(login)) {
            respType = "User with this login already exists. Choose another login.";
            outStream.write(respType.getBytes(StandardCharsets.UTF_8));
            outStream.flush();
            outStream.close();
            return;
        }

        user = new User(login, password, Role.DEFAULT);
        userDao.create(user);
        req.getSession().setAttribute("logged", true);
        req.getSession().setAttribute("login", login);

        respType = "success";
        outStream.write(respType.getBytes(StandardCharsets.UTF_8));
        outStream.flush();
        outStream.close();
        resp.sendRedirect("/home");
    }
}
