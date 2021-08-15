package by.bsu.d0mpi.UP_PostGallery.command.action;

import by.bsu.d0mpi.UP_PostGallery.command.Command;
import by.bsu.d0mpi.UP_PostGallery.command.CommandRequest;
import by.bsu.d0mpi.UP_PostGallery.command.CommandResponse;
import by.bsu.d0mpi.UP_PostGallery.command.SimpleCommandResponse;
import by.bsu.d0mpi.UP_PostGallery.exception.SessionAttributeNotFoundException;
import by.bsu.d0mpi.UP_PostGallery.exception.SessionNotFoundException;
import by.bsu.d0mpi.UP_PostGallery.model.Like;
import by.bsu.d0mpi.UP_PostGallery.model.Post;
import by.bsu.d0mpi.UP_PostGallery.model.User;
import by.bsu.d0mpi.UP_PostGallery.service.LikeService;
import by.bsu.d0mpi.UP_PostGallery.service.PostService;
import by.bsu.d0mpi.UP_PostGallery.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import static by.bsu.d0mpi.UP_PostGallery.command.action.DeletePostAction.REQUEST_POST_ID_PARAM;
import static by.bsu.d0mpi.UP_PostGallery.command.page.ShowPostEditPage.SESSION_USER_NAME;

/**
 * Implementation of the Command interface, which is responsible
 * for changing like checkbox and post's author stats.
 * Implements thread-safe Singleton pattern using double checked locking idiom.
 *
 * @author d0mpi
 * @version 1.0
 * @see Command
 * @see CommandResponse
 * @see UserService
 * @see LikeService
 */
public class LikeAction implements Command {
    private static final Logger logger = LogManager.getLogger();

    public static final String REQUEST_IS_LIKE_PARAM = "isLike";
    private static volatile LikeAction instance;

    private final CommandResponse doNothing;
    private final UserService userService;
    private final LikeService likeService;

    private LikeAction() {
        doNothing = new SimpleCommandResponse("/controller?command=main_page", false, true);
        userService = UserService.simple();
        likeService = LikeService.simple();
    }

    /**
     * Provide a global access point to the instance of the {@link LikeAction} class.
     *
     * @return the only instance of the {@link LikeAction} class
     */
    public static LikeAction getInstance() {
        LikeAction localInstance = instance;
        if (localInstance == null) {
            synchronized (LikeAction.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new LikeAction();
                }
            }
        }
        return localInstance;
    }

    /**
     * Changes the like checkbox in the posts feed and in the database.
     * Changes the statistics of the post's author.
     * Changing the like works using ajax.
     *
     * @param request the object contains a request received from the client
     * @return an object of the {@link CommandResponse} class which does nothing
     */
    @Override
    public CommandResponse execute(CommandRequest request) {
        boolean isLike = Boolean.parseBoolean(request.getParameter(REQUEST_IS_LIKE_PARAM));
        int postId = Integer.parseInt(request.getParameter(REQUEST_POST_ID_PARAM));
        HttpSession session = request.getCurrentSession().orElse(null);
        if(session == null){
            return doNothing;
        }

        String userName = (String) session.getAttribute(SESSION_USER_NAME);
        if(isLike){
            likeService.createEntity(new Like(postId, userName));
        } else {
            likeService.delete(postId, userName);
        }

        return doNothing;
    }
}
