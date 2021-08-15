package by.bsu.d0mpi.UP_PostGallery.command.action;

import by.bsu.d0mpi.UP_PostGallery.command.Command;
import by.bsu.d0mpi.UP_PostGallery.command.CommandRequest;
import by.bsu.d0mpi.UP_PostGallery.command.CommandResponse;
import by.bsu.d0mpi.UP_PostGallery.command.SimpleCommandResponse;
import by.bsu.d0mpi.UP_PostGallery.model.Post;
import by.bsu.d0mpi.UP_PostGallery.model.User;
import by.bsu.d0mpi.UP_PostGallery.service.PostService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

/**
 * Implementation of the Command interface, which is responsible
 * for editing the {@link Post} in the database and the feed of posts
 * by a registered {@link User} using {@link AddPostAction#execute} method.
 * Implements thread-safe Singleton pattern using double checked locking idiom.
 *
 * @author d0mpi
 * @version 1.0
 * @see Command
 * @see CommandResponse
 * @see PostService
 * @see Post
 */
public class EditPostAction implements Command {
    private static final Logger logger = LogManager.getLogger();
    public static final String SESSION_EDIT_POST_ID = "lastEditPostId";
    private static volatile EditPostAction instance;

    private final CommandResponse redirectHomePage;
    private final PostService postService;

    private EditPostAction() {
        redirectHomePage = new SimpleCommandResponse("/controller?command=main_page", true);
        postService = PostService.simple();
    }

    /**
     * Provide a global access point to the instance of the {@link DeletePostAction} class.
     *
     * @return the only instance of the {@link DeletePostAction} class
     */
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

    /**
     * Parses post data from the received request and changes post information.
     *
     * @param request the object contains a request received from the client
     * @return an object of the {@link CommandResponse} class with redirection to the main page
     * after successful or unsuccessful editing the post.
     */
    @Override
    public CommandResponse execute(CommandRequest request) {
        HttpSession session = request.getCurrentSession().orElse(null);

        if (session == null) {
            return redirectHomePage;
        }
        Integer postId = (Integer) session.getAttribute(SESSION_EDIT_POST_ID);
        if (postId == null) {
            return redirectHomePage;
        }
        session.removeAttribute(SESSION_EDIT_POST_ID);

        Post post = postService.findEntityById(postId);

        if (post == null) {
            return redirectHomePage;
        }
        post.setModel(request.getParameter("model"));
        post.setType(request.getParameter("type"));
        post.setLength(new BigDecimal(request.getParameter("lengthInput").replace(',', '.')));
        post.setWingspan(new BigDecimal(request.getParameter("wingspan").replace(',', '.')));
        post.setHeight(new BigDecimal(request.getParameter("height").replace(',', '.')));
        post.setOrigin(request.getParameter("origin"));
        post.setCrew(Integer.valueOf(request.getParameter("crew")));
        post.setSpeed(new BigDecimal(request.getParameter("speed").replace(',', '.')));
        post.setDistance(new BigDecimal(request.getParameter("dist").replace(',', '.')));
        post.setPrice(new BigDecimal(request.getParameter("price").replace(',', '.')));
        List<String> hashtags = AddPostAction.getInstance().parseHashtagsListFromRequest(request);

        try {
            AddPostAction.getInstance().uploadImageToDirectory(request, post.getId());
        } catch (ServletException | IOException e) {
            logger.error("Error during uploading image to the server file system!");
            return redirectHomePage;
        }

        post.setHashtags(hashtags);
        postService.editEntity(post);

        return redirectHomePage;
    }
}
