package by.bsu.d0mpi.UP_PostGallery.servlet;

import by.bsu.d0mpi.UP_PostGallery.dao.impl.MySqlPostDaoImpl;
import by.bsu.d0mpi.UP_PostGallery.model.Post;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "home", value = "/home")
public class HomeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession httpSession = req.getSession();
        httpSession.setAttribute("logged", true);


        req.setAttribute("postList", new ArrayList<>(MySqlPostDaoImpl.getInstance().findAll()));

        RequestDispatcher requestDispatcher = req.getRequestDispatcher("index.jsp");
        requestDispatcher.forward(req,resp);
    }
}
