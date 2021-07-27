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


public class LikeAction implements Command {
    private static final Logger logger = LogManager.getLogger();
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
        boolean isLike = Boolean.parseBoolean(request.getParameter("isLike"));
        int postId = Integer.parseInt(request.getParameter("post_id"));
        HttpSession session = request.getCurrentSession().orElse(null);
        if(session == null){
            return doNothing;
        }

        String userName = (String) session.getAttribute("user_name");


        if(isLike){
            likeService.createEntity(new Like(postId, userName));
        } else {
            likeService.delete(postId, userName);
        }

        return doNothing;
    }
}
