package by.bsu.d0mpi.UP_PostGallery.command.action;

import by.bsu.d0mpi.UP_PostGallery.command.Command;
import by.bsu.d0mpi.UP_PostGallery.command.CommandRequest;
import by.bsu.d0mpi.UP_PostGallery.command.CommandResponse;
import by.bsu.d0mpi.UP_PostGallery.command.SimpleCommandResponse;
import by.bsu.d0mpi.UP_PostGallery.model.Post;
import by.bsu.d0mpi.UP_PostGallery.model.Role;
import by.bsu.d0mpi.UP_PostGallery.model.User;
import by.bsu.d0mpi.UP_PostGallery.service.PostService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpSession;

import static by.bsu.d0mpi.UP_PostGallery.command.page.ShowPostEditPage.SESSION_USER_NAME;
import static by.bsu.d0mpi.UP_PostGallery.command.page.ShowPostEditPage.SESSION_USER_ROLE;

/**
 * Implementation of the Command interface, which is responsible
 * for deleting a {@link Post} from the database and the feed of posts
 * by a registered {@link User} using {@link AddPostAction#execute} method.
 * Implements thread-safe Singleton pattern using double checked locking idiom.
 *
 * @author d0mpi
 * @version 1.0
 * @see Command
 * @see CommandResponse
 * @see PostService
 */
public class DeletePostAction implements Command {
    private static final Logger logger = LogManager.getLogger();
    public static final String REQUEST_POST_ID_PARAM = "post_id";
    private static volatile DeletePostAction instance;

    private final CommandResponse redirectHomePage;
    private final PostService postService;

    private DeletePostAction() {
        redirectHomePage = new SimpleCommandResponse("/controller?command=main_page", false);
        postService = PostService.simple();
    }

    /**
     * Provide a global access point to the instance of the {@link DeletePostAction} class.
     *
     * @return the only instance of the {@link DeletePostAction} class
     */
    public static DeletePostAction getInstance() {
        DeletePostAction localInstance = instance;
        if (localInstance == null) {
            synchronized (DeletePostAction.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new DeletePostAction();
                }
            }
        }
        return localInstance;
    }

    /**
     * Deletes post from the post feed and the database.
     * Only author of the post, admin and moderator can delete post.
     *
     * @param request the object contains a request received from the client
     * @return an object of the {@link CommandResponse} class with redirection to the main page
     * after successful or unsuccessful deleting the post
     */
    @Override
    public CommandResponse execute(CommandRequest request) {
        Integer postId;
        HttpSession session = request.getCurrentSession().orElse(null);
        try {
            postId = Integer.valueOf(request.getParameter(REQUEST_POST_ID_PARAM));
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return redirectHomePage;
        }
        if (session == null ||
                postService.findEntityById(postId) == null ||
                postService.postDoesNotBelongToUser(postId, (String) session.getAttribute(SESSION_USER_NAME)) &&
                        !(session.getAttribute(SESSION_USER_ROLE).toString().equals(Role.ADMIN.toString()) ||
                                session.getAttribute(SESSION_USER_ROLE).toString().equals(Role.MODERATOR.toString()))) {
            return redirectHomePage;
        }
        postService.delete(postId);
        return redirectHomePage;
    }
}
