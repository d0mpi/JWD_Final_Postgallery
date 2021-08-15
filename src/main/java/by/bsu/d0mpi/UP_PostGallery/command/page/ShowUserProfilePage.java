package by.bsu.d0mpi.UP_PostGallery.command.page;

import by.bsu.d0mpi.UP_PostGallery.command.Command;
import by.bsu.d0mpi.UP_PostGallery.command.CommandRequest;
import by.bsu.d0mpi.UP_PostGallery.command.CommandResponse;
import by.bsu.d0mpi.UP_PostGallery.command.SimpleCommandResponse;
import by.bsu.d0mpi.UP_PostGallery.command.action.AddPostAction;
import by.bsu.d0mpi.UP_PostGallery.service.PostService;
import by.bsu.d0mpi.UP_PostGallery.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpSession;

import static by.bsu.d0mpi.UP_PostGallery.command.page.ShowPostEditPage.SESSION_USER_NAME;

/**
 * Implementation of the Command interface, which is responsible
 * for the forwarding to the user's profile page.
 * Implements thread-safe Singleton pattern using double checked locking idiom.
 *
 * @author d0mpi
 * @version 1.0
 * @see Command
 * @see CommandResponse
 * @see CommandRequest
 * @see UserService
 */
public class ShowUserProfilePage implements Command {
    private static final Logger logger = LogManager.getLogger();
    private static volatile ShowUserProfilePage instance;

    private final CommandResponse forwardProfilePage;
    private final UserService userService;


    private ShowUserProfilePage() {
        forwardProfilePage = new SimpleCommandResponse("/WEB-INF/views/profile.jsp", false);
        userService = UserService.simple();
    }

    /**
     * Provide a global access point to the instance of the {@link ShowUserProfilePage} class.
     *
     * @return the only instance of the {@link ShowUserProfilePage} class
     */
    public static ShowUserProfilePage getInstance() {
        ShowUserProfilePage localInstance = instance;
        if (localInstance == null) {
            synchronized (ShowUserProfilePage.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new ShowUserProfilePage();
                }
            }
        }
        return localInstance;
    }

    /**
     * Retrieves user statistics from the database and forwards user to the user's profile page.
     *
     * @param request the object contains a request received from the client
     * @return an object of the {@link CommandResponse} class with forwarding to the user's profile page.
     */
    @Override
    public CommandResponse execute(CommandRequest request) {
        HttpSession session = request.getCurrentSession().orElse(null);
        if (session != null) {
            int numberOfPosts = userService.getNumberOfPosts(String.valueOf(session.getAttribute(SESSION_USER_NAME)));
            request.setAttribute("number_of_posts", numberOfPosts);
            int numberOfLikes = userService.getRating(String.valueOf(session.getAttribute(SESSION_USER_NAME)));
            request.setAttribute("number_of_likes", numberOfLikes);
        }
        return forwardProfilePage;
    }
}
