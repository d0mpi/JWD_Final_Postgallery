package by.bsu.d0mpi.UP_PostGallery.servlet;

import by.bsu.d0mpi.UP_PostGallery.dao.impl.MySqlPostDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "delete", value = "/delete")
public class DeleteServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/plain");
        req.getSession().setAttribute("currPageSize",(int)req.getSession().getAttribute("currPageSize") - 1);
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write("true");
        MySqlPostDao postDao = MySqlPostDao.getInstance();
        postDao.delete(Integer.valueOf(req.getParameter("id")));
        resp.getWriter().write("true");
    }
}
