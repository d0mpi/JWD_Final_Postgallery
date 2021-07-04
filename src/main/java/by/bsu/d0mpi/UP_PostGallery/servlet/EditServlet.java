package by.bsu.d0mpi.UP_PostGallery.servlet;

import by.bsu.d0mpi.UP_PostGallery.dao.impl.MySqlPostDao;
import by.bsu.d0mpi.UP_PostGallery.exception.DAOException;
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
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet(name = "edit", value = "/edit")
public class EditServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        MySqlPostDao postDao = MySqlPostDao.getInstance();
        Post post = postDao.findEntityById(Integer.valueOf(req.getParameter("edit")));

        HttpSession httpSession = req.getSession();
        httpSession.setAttribute("lastEditId",post.getId());
        req.setAttribute("postToEdit", post);
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/WEB-INF/views/edit.jsp");
        requestDispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        MySqlPostDao postDao = MySqlPostDao.getInstance();

        Post post = postDao.findEntityById((Integer) req.getSession().getAttribute("lastEditId"));

        post.setModel(req.getParameter("model"));
        post.setType(req.getParameter("type"));
        post.setLength(Float.valueOf(req.getParameter("lengthInput")));
        post.setWingspan(Float.valueOf(req.getParameter("wingspan")));
        post.setHeight(Float.valueOf(req.getParameter("height")));
        post.setOrigin(req.getParameter("origin"));
        post.setCrew(Integer.valueOf(req.getParameter("crew")));
        post.setSpeed(Float.valueOf(req.getParameter("speed")));
        post.setDistance(Float.valueOf(req.getParameter("dist")));
        post.setPrice(Integer.valueOf(req.getParameter("price")));
        post.setPhotoLink(req.getParameter("file"));
        List<String> hashtags;
        if (req.getParameter("hashtags") != null && !req.getParameter("hashtags").equals("")) {
            hashtags = Arrays.stream(
                    req.getParameter("hashtags").
                            split(" ")).distinct().collect(Collectors.toList());
        } else {
            hashtags = new ArrayList<>();
        }

        post.setHashtags(hashtags);
        postDao.update(post);

        resp.sendRedirect("/home");
    }
}