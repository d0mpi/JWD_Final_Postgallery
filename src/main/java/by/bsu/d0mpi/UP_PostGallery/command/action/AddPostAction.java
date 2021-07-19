package by.bsu.d0mpi.UP_PostGallery.command.action;

import by.bsu.d0mpi.UP_PostGallery.command.Command;
import by.bsu.d0mpi.UP_PostGallery.command.CommandRequest;
import by.bsu.d0mpi.UP_PostGallery.command.CommandResponse;
import by.bsu.d0mpi.UP_PostGallery.command.SimpleCommandResponse;
import by.bsu.d0mpi.UP_PostGallery.dao.impl.MySqlPostDao;
import by.bsu.d0mpi.UP_PostGallery.model.Post;
import by.bsu.d0mpi.UP_PostGallery.service.PostService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class AddPostAction implements Command {
    private static final Logger LOGGER = LogManager.getLogger();
    private static volatile AddPostAction instance;

    private final CommandResponse redirectHomePage;
    private final CommandResponse redirectErrorPage;

    public AddPostAction() {
        redirectHomePage = new SimpleCommandResponse("/controller?command=main_page", true);
        redirectErrorPage = new SimpleCommandResponse("/controller?command=error_page", true);
    }

    public static AddPostAction getInstance() {
        AddPostAction localInstance = instance;
        if (localInstance == null) {
            synchronized (AddPostAction.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new AddPostAction();
                }
            }
        }
        return localInstance;
    }

    @Override
    public CommandResponse execute(CommandRequest request) {
        PostService postService = PostService.simple();

        String model = request.getParameter("model");
        String type = request.getParameter("type");
        Float length = (request.getParameter("lengthInput").equals("") ||
                request.getParameter("lengthInput") == null) ? 0 : Float.parseFloat(request.getParameter("lengthInput"));
        Float wingspan = (request.getParameter("wingspan").equals("") ||
                request.getParameter("wingspan") == null) ? 0 : Float.parseFloat(request.getParameter("wingspan"));
        Float height = (request.getParameter("height").equals("") ||
                request.getParameter("height") == null) ? 0 : Float.parseFloat(request.getParameter("height"));
        String origin = request.getParameter("origin");
        Integer crew = (request.getParameter("crew").equals("") ||
                request.getParameter("crew") == null) ? 0 : Integer.parseInt(request.getParameter("crew"));
        Float speed = (request.getParameter("speed").equals("") ||
                request.getParameter("speed") == null) ? 0 : Float.parseFloat(request.getParameter("speed"));
        Float distance = (request.getParameter("dist").equals("") ||
                request.getParameter("dist") == null) ? 0 : Float.parseFloat(request.getParameter("dist"));
        Integer price = Integer.valueOf(request.getParameter("price"));
        ResourceBundle resource = ResourceBundle.getBundle("database");
        LocalDate createdAt = LocalDate.now();


        HttpSession session = request.getCurrentSession().orElse(null);
        if (session == null) {
            return redirectErrorPage;
        }
        String author = (String) session.getAttribute("user_name");

        String photoLink = "images/planes/" + request.getParameter("file");
        List<String> hashtags;
        if (request.getParameter("hashtags") != null && !request.getParameter("hashtags").equals("")) {
            hashtags = Arrays.stream(
                    request.getParameter("hashtags").
                            split(" ")).distinct().collect(Collectors.toList());
        } else {
            hashtags = new ArrayList<>();
        }
        Post post = new Post(model, type, length, wingspan, height, origin, crew, speed, distance, price, createdAt, author, photoLink, hashtags);
        postService.createEntity(post);
        return redirectHomePage;
    }
}
