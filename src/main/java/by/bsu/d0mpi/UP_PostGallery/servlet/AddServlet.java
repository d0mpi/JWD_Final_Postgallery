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
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;


@WebServlet(name = "add-post", value = "/add-post")
public class AddServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/WEB-INF/views/add-post.jsp");
        requestDispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        MySqlPostDao postDao = MySqlPostDao.getInstance();

        String model = req.getParameter("model");
        String type = req.getParameter("type");
        Float length = (req.getParameter("lengthInput").equals("") ||
                req.getParameter("lengthInput") == null) ? 0 : Float.parseFloat(req.getParameter("lengthInput"));
        Float wingspan = (req.getParameter("wingspan").equals("") ||
                req.getParameter("wingspan") == null) ? 0 : Float.parseFloat(req.getParameter("wingspan"));
        Float height = (req.getParameter("height").equals("") ||
                req.getParameter("height") == null) ? 0 : Float.parseFloat(req.getParameter("height"));
        String origin = req.getParameter("origin");
        Integer crew = (req.getParameter("crew").equals("") ||
                req.getParameter("crew") == null) ? 0 : Integer.parseInt(req.getParameter("crew"));
        Float speed = (req.getParameter("speed").equals("") ||
                req.getParameter("speed") == null) ? 0 : Float.parseFloat(req.getParameter("speed"));
        Float distance = (req.getParameter("dist").equals("") ||
                req.getParameter("dist") == null) ? 0 : Float.parseFloat(req.getParameter("dist"));
        Integer price = Integer.valueOf(req.getParameter("price"));
        ResourceBundle resource = ResourceBundle.getBundle("database");
        LocalDate createdAt = LocalDate.now();
        String author = (String) req.getSession().getAttribute("login");
        String photoLink = getServletContext().getInitParameter("path_to_image") + req.getParameter("file");
        List<String> hashtags;
        if (req.getParameter("hashtags") != null && !req.getParameter("hashtags").equals("")) {
            hashtags = Arrays.stream(
                    req.getParameter("hashtags").
                            split(" ")).distinct().collect(Collectors.toList());
        } else {
            hashtags = new ArrayList<>();
        }

        Post post = new Post(model, type, length, wingspan, height, origin, crew, speed, distance, price, createdAt, author, photoLink, hashtags);
        postDao.create(post);

        resp.sendRedirect("/home");
    }
}