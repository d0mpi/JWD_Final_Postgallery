package by.bsu.d0mpi.UP_PostGallery.command.action;

import by.bsu.d0mpi.UP_PostGallery.command.Command;
import by.bsu.d0mpi.UP_PostGallery.command.CommandRequest;
import by.bsu.d0mpi.UP_PostGallery.command.CommandResponse;
import by.bsu.d0mpi.UP_PostGallery.command.SimpleCommandResponse;
import by.bsu.d0mpi.UP_PostGallery.model.Post;
import by.bsu.d0mpi.UP_PostGallery.service.PostService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class EditPostAction implements Command {
    private static final Logger LOGGER = LogManager.getLogger();
    private static volatile EditPostAction instance;

    private final CommandResponse redirectHomePage;
    private final PostService postService;

    public EditPostAction() {
        redirectHomePage = new SimpleCommandResponse("/controller?command=main_page", true);
        postService = PostService.simple();
    }

    public static EditPostAction getInstance() {
        EditPostAction localInstance = instance;
        if (localInstance == null) {
            synchronized (EditPostAction.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new EditPostAction();
                }
            }
        }
        return localInstance;
    }

    @Override
    public CommandResponse execute(CommandRequest request) {
        HttpSession session = request.getCurrentSession().orElse(null);

        if (session == null) {
            return redirectHomePage;
        }
        Integer postId = (Integer) session.getAttribute("lastEditPostId");
        if (postId == null) {
            return redirectHomePage;
        }
        session.removeAttribute("lastEditPostId");

        Post post = postService.findEntityById(postId);

        if (post == null) {
            return redirectHomePage;
        }

        post.setModel(request.getParameter("model"));
        post.setType(request.getParameter("type"));
        post.setLength(Float.valueOf(request.getParameter("lengthInput")));
        post.setWingspan(Float.valueOf(request.getParameter("wingspan")));
        post.setHeight(Float.valueOf(request.getParameter("height")));
        post.setOrigin(request.getParameter("origin"));
        post.setCrew(Integer.valueOf(request.getParameter("crew")));
        post.setSpeed(Float.valueOf(request.getParameter("speed")));
        post.setDistance(Float.valueOf(request.getParameter("dist")));
        post.setPrice(Integer.valueOf(request.getParameter("price")));
        List<String> hashtags;
        if (request.getParameter("hashtags") != null && !request.getParameter("hashtags").equals("")) {
            hashtags = Arrays.stream(
                    request.getParameter("hashtags").
                            split(" ")).distinct().collect(Collectors.toList());
        } else {
            hashtags = new ArrayList<>();
        }

        post.setHashtags(hashtags);
        postService.editEntity(post);

        return redirectHomePage;
    }
}
