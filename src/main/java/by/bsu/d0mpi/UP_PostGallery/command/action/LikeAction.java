package by.bsu.d0mpi.UP_PostGallery.command.action;

import by.bsu.d0mpi.UP_PostGallery.command.Command;
import by.bsu.d0mpi.UP_PostGallery.command.CommandRequest;
import by.bsu.d0mpi.UP_PostGallery.command.CommandResponse;
import by.bsu.d0mpi.UP_PostGallery.command.SimpleCommandResponse;
import by.bsu.d0mpi.UP_PostGallery.model.Like;
import by.bsu.d0mpi.UP_PostGallery.model.User;
import by.bsu.d0mpi.UP_PostGallery.service.LikeService;
import by.bsu.d0mpi.UP_PostGallery.service.PostService;
import by.bsu.d0mpi.UP_PostGallery.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpSession;

import static by.bsu.d0mpi.UP_PostGallery.command.action.DeletePostAction.REQUEST_POST_ID_PARAM;
import static by.bsu.d0mpi.UP_PostGallery.command.page.ShowPostEditPage.SESSION_USER_NAME;


public class LikeAction implements Command {
    private static final Logger logger = LogManager.getLogger();
    public static final String REQUEST_IS_LIKE_PARAM = "isLike";
    private static volatile LikeAction instance;

    private final CommandResponse doNothing;
    private final UserService userService;
    private final LikeService likeService;

    public LikeAction() {
        doNothing = new SimpleCommandResponse("/controller?command=main_page", false, true);
        userService = UserService.simple();
        likeService = LikeService.simple();
    }

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
