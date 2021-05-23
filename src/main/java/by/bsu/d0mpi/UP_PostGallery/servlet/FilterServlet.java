package by.bsu.d0mpi.UP_PostGallery.servlet;

import by.bsu.d0mpi.UP_PostGallery.dao.impl.MySqlPostDaoImpl;
import by.bsu.d0mpi.UP_PostGallery.model.Post;
import lombok.SneakyThrows;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@WebServlet(name = "filter", value = "/filter")
public class FilterServlet extends HttpServlet {


    @SneakyThrows
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        MySqlPostDaoImpl postDao = MySqlPostDaoImpl.getInstance();
        ArrayList<Post> postList = postDao.findAll();
        Stream<Post> postStream = postList.stream();
        String[] filters = req.getParameterValues("checkbox");

        System.out.println(Arrays.toString(filters));

        if(filters != null && filters.length != 0) {
            for (String filterName : filters) {
                if (filterName.equals("filter_author_checkbox")) {
                    final String author = req.getParameter("filter_author_text");
                    if (author != null && !author.equals("")) {
                        postStream = postStream.filter(o -> o.getAuthor().equals(author));
                    }
                }
                if(filterName.equals("filter_hashtags_checkbox")){
                    final String hashtag = req.getParameter("filter_hashtags_text");
                    if(hashtag != null && !hashtag.equals("")){
                        postStream = postStream.filter(o -> o.getHashtags().contains(hashtag));
                    }
                }
                if(filterName.equals("filter_date_checkbox")){
                    final LocalDate date = LocalDate.parse(req.getParameter("filter_date_text"));
                    System.out.println(date);
                    if(date != null){
                        postStream = postStream.filter(o -> o.getCreatedAt().isEqual(date));
                    }
                }
            }
        }
        postList = (ArrayList<Post>) postStream.collect(Collectors.toList());
        req.setAttribute("postList", postList);
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("index.jsp");
        requestDispatcher.forward(req, resp);
    }
}
