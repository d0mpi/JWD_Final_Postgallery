package by.bsu.d0mpi.UP_PostGallery.servlet;
import by.bsu.d0mpi.UP_PostGallery.dao.impl.MySqlPostDao;
import by.bsu.d0mpi.UP_PostGallery.model.Post;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "home", value = "/home")
public class HomeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ArrayList<Post> postArrayList =  MySqlPostDao.getInstance().findAll();
        req.setAttribute("postList", postArrayList.subList(0, (Math.min(postArrayList.size(), 10))));
        req.getSession().setAttribute("currPageSize", Math.min(postArrayList.size(), 10));
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/WEB-INF/views/index.jsp");
        requestDispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        MySqlPostDao postDao = MySqlPostDao.getInstance();

        if (req.getParameter("delete") != null) {
            postDao.delete(Integer.valueOf(req.getParameter("delete")));
        } else if(req.getParameter("sign-out") != null){
            req.getSession().removeAttribute("login");
            req.getSession().removeAttribute("logged");
        }

       resp.sendRedirect("/home");
    }
}
