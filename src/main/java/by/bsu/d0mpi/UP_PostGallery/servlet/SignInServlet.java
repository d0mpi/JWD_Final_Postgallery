package by.bsu.d0mpi.UP_PostGallery.servlet;

import by.bsu.d0mpi.UP_PostGallery.dao.impl.MySqlPostDaoImpl;
import by.bsu.d0mpi.UP_PostGallery.dao.impl.MySqlUserDaoImpl;
import by.bsu.d0mpi.UP_PostGallery.model.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "sign", value = "/sign")
public class SignInServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("sign.jsp");
        requestDispatcher.forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("username");
        String password = req.getParameter("password");
        MySqlUserDaoImpl userDao = MySqlUserDaoImpl.getInstance();
        User user;
        if(!userDao.isLoginPresented(login)) {
            resp.sendRedirect("/sign");
            return;
        } else {
            user = userDao.getUserByLogin(login);
            if (!user.getPassword().equals(password)) {
                resp.sendRedirect("/sign");
                return;
            }
        }
        req.getSession().setAttribute("logged", true);
        req.getSession().setAttribute("login", login);

        resp.sendRedirect("/home");
    }
}
