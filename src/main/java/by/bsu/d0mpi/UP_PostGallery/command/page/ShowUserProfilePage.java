package by.bsu.d0mpi.UP_PostGallery.command.page;

import by.bsu.d0mpi.UP_PostGallery.command.Command;
import by.bsu.d0mpi.UP_PostGallery.command.CommandRequest;
import by.bsu.d0mpi.UP_PostGallery.command.CommandResponse;
import by.bsu.d0mpi.UP_PostGallery.command.SimpleCommandResponse;
import by.bsu.d0mpi.UP_PostGallery.service.PostService;
import by.bsu.d0mpi.UP_PostGallery.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpSession;

public class ShowUserProfilePage implements Command {
    private static final Logger LOGGER = LogManager.getLogger();
    private static volatile ShowUserProfilePage instance;

    private final CommandResponse forwardProfilePage;
    private final UserService userService;

    public ShowUserProfilePage() {
        forwardProfilePage = new SimpleCommandResponse("/WEB-INF/views/profile.jsp", false);
        userService = UserService.simple();
    }

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

    @Override
    public CommandResponse execute(CommandRequest request) {
        HttpSession session = request.getCurrentSession().orElse(null);
        if (session != null) {
            int numberOfPosts = userService.getNumberOfPosts(String.valueOf(session.getAttribute("user_name")));
            request.setAttribute("number_of_posts", numberOfPosts);
            int numberOfLikes = userService.getRating(String.valueOf(session.getAttribute("user_name")));
            request.setAttribute("number_of_likes", numberOfLikes);
        }
        return forwardProfilePage;
    }
}
