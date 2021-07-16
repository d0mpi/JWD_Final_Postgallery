package by.bsu.d0mpi.UP_PostGallery.servlet;

import by.bsu.d0mpi.UP_PostGallery.dao.impl.MySqlPostDao;
import by.bsu.d0mpi.UP_PostGallery.exception.DAOException;
import by.bsu.d0mpi.UP_PostGallery.model.Post;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

@WebServlet(name = "more", value = "/more")
public class MoreServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/plain");
        String delete = "false";
        OutputStream outStream = resp.getOutputStream();
        if (MySqlPostDao.getInstance().findAll().size() <= (int) req.getSession().getAttribute("currPageSize")) {
            delete = "true";
        }
        outStream.write(delete.getBytes(StandardCharsets.UTF_8));
        outStream.flush();
        outStream.close();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html");
        int currSize = (int) req.getSession().getAttribute("currPageSize");
        List<Post> postArrayList = MySqlPostDao.getInstance().findAll();
        int newSize = currSize;
        if (postArrayList.size() > currSize) {
            newSize = Math.min(postArrayList.size(), currSize + 10);
        }
        StringBuilder stringBuilder = new StringBuilder();
        Boolean logged = (Boolean) req.getSession().getAttribute("logged");
        String login = (String) req.getSession().getAttribute("login");

        for (Post post : postArrayList.subList(currSize, newSize)) {
            String html =
                    "                    <div class=\"card-box\">\n" +
                            "                        <div class=\"card-main-col\">\n" +
                            "                            <div class=\"card-main-box col-full\">\n" +
                            "                                <div class=\"card-img-box\">\n" +
                            "                                    <img src=\"" + post.getPhotoLink() + "\" alt=\"samolet\" class=\"card-img\">\n" +
                            "                                </div>\n" +
                            "                                <div class=\"card-text-box\">\n" +
                            "                                    <ul class=\"card-text-top font-card\">\n" +
                            "                                        <li><span>Model:</span> " + (post.getModel().equals("") ? "-" : post.getModel()) + "  </li>\n" +
                            "                                        <li><span>Type:</span> " + (post.getType().equals("") ? "-" : post.getType()) + " </li>\n" +
                            "                                        <li><span>Length:</span> " + (post.getLength() == 0 ? "-" : post.getLength()) + "</li>\n" +
                            "                                        <li><span>Wingspan:</span> " + (post.getWingspan() == 0 ? "-" : post.getWingspan()) + "</li>\n" +
                            "                                        <li><span>Height:</span> " + (post.getHeight() == 0 ? "-" : post.getHeight()) + "</li>\n" +
                            "                                        <li><span>Origin:</span> " + (post.getOrigin().equals("") ? "-" : post.getOrigin()) + "</li>\n" +
                            "                                        <li><span>Crew:</span> " + (post.getCrew() == 0 ? "-" : post.getCrew()) + "</li>\n" +
                            "                                        <li><span>Max speed:</span> " + (post.getSpeed() == 0 ? "-" : post.getSpeed()) + " km/h</li>\n" +
                            "                                        <li><span>Flying dist:</span> " + (post.getDistance() == 0 ? "-" : post.getDistance()) + " km\n" +
                            "                                        </li>\n" +
                            "                                        <li><span>Price:</span> " + (post.getPrice() == 0 ? "-" : post.getPrice()) + "$</li>\n" +
                            "                                        <li>\n" +
                            "                                            <hr class=\"card-text-hr col-full\">\n" +
                            "                                        </li>\n" +
                            "                                    </ul>\n" +
                            "                                    <div class=\"card-text-bottom-box\">\n" +
                            "                                        <ul class=\"card-text-bottom-left\">\n" +
                            "                                            <li class=\"card-text-user\"> " + post.getAuthor() + " </li>\n" +
                            "                                            <li class=\"card-id\"> id: " + post.getId() + "</li>\n" +
                            "                                            <li class=\"card-text-time\"> " + post.getCreatedAt() + "</li>\n" +
                            "                                        </ul>\n" + ((logged != null && logged) ?
                            "                                                <form id=\"like-form" + post.getId() + "\"\n" +
                                    "                                                      action=\"" + req.getContextPath() + "/like\" method=\"post\">\n" +
                                    "                                                    <input id=\"like-check" + post.getId() + "\" class=\"like-check\" type=\"checkbox\"" + ("checked") + " value=\"\">\n" +
                                    "                                                    <label for=\"like-check" + post.getId() + "\" class=\"like-label\"></label>\n" +
                                    "                                                </form>\n" : "") +
                            "                                    </div>\n" +
                            "\n" +
                            "                                </div>\n" +
                            "                            </div>\n" +
                            "\n" +
                            "                            <div class=\"card-hashtags col-full\">\n" +
                            "                                    " + post.getHashtagsAsHashString() + "\n" +
                            "                            </div>\n" +
                            "                        </div>\n" +
                            "\n" +
                            "                        <div class=\"card-buttons-col\">\n" + ((logged != null && logged && login != null && login.equals(post.getAuthor())) ?
                            "                                        <form action=\"" + req.getContextPath() + "/edit\" method=\"get\"\n" +
                                    "                                              name=\"editForm" + post.getId() + "\">\n" +
                                    "                                            <button type=\"submit\" name=\"edit\" value=\"" + post.getId() + "\"\n" +
                                    "                                                    class=\"card-edit-button\" title=\"\"></button>\n" +
                                    "                                        </form>\n" +
                                    "                                        <form  method=\"post\"\n" +
                                    "                                              name=\"" + post.getId() + "\">\n" +
                                    "                                            <button type=\"button\" name=\"delete\" value=\"" + post.getId() + "\"\n" +
                                    "                                                    class=\"card-delete-button\" title=\"\"></button>\n" +
                                    "                                        </form>\n" : "") +
                            "                        </div>\n" +
                            "                    </div>";
            stringBuilder.append(html);
        }


        req.getSession().setAttribute("currPageSize", newSize);
        resp.getWriter().write(stringBuilder.toString());

    }
}
