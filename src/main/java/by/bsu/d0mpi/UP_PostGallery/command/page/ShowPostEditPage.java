package by.bsu.d0mpi.UP_PostGallery.command.page;

import by.bsu.d0mpi.UP_PostGallery.command.Command;
import by.bsu.d0mpi.UP_PostGallery.command.CommandRequest;
import by.bsu.d0mpi.UP_PostGallery.command.CommandResponse;
import by.bsu.d0mpi.UP_PostGallery.command.SimpleCommandResponse;
import by.bsu.d0mpi.UP_PostGallery.model.Post;
import by.bsu.d0mpi.UP_PostGallery.model.Role;
import by.bsu.d0mpi.UP_PostGallery.service.PostService;
import by.bsu.d0mpi.UP_PostGallery.service.impl.SimplePostService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpSession;

import static by.bsu.d0mpi.UP_PostGallery.command.action.DeletePostAction.REQUEST_POST_ID_PARAM;
import static by.bsu.d0mpi.UP_PostGallery.command.action.EditPostAction.SESSION_EDIT_POST_ID;

public class ShowPostEditPage implements Command {
    private static final Logger logger = LogManager.getLogger();
    public static final String POST_TO_EDIT = "postToEdit";
    public static final String SESSION_USER_NAME = "user_name";
    public static final String SESSION_USER_ROLE = "current_role";
    private static volatile ShowPostEditPage instance;

    private final PostService postService;
    private final CommandResponse redirectHomePage;
    private final CommandResponse forwardEditPage;

    public ShowPostEditPage() {
        postService = new SimplePostService();
        redirectHomePage = new SimpleCommandResponse("/controller?command=main_page", true);
        forwardEditPage = new SimpleCommandResponse("/WEB-INF/views/edit.jsp", false);
    }

    public static ShowPostEditPage getInstance() {
        ShowPostEditPage localInstance = instance;
        if (localInstance == null) {
            synchronized (ShowPostEditPage.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new ShowPostEditPage();
                }
            }
        }
        return localInstance;
    }

    @Override
    public CommandResponse execute(CommandRequest request) {
        Integer postId = null;
        HttpSession session = request.getCurrentSession().orElse(null);
        try {
            postId = Integer.valueOf(request.getParameter(REQUEST_POST_ID_PARAM));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        if (postId == null ||
                session == null ||
                postService.findEntityById(postId) == null ||
                (postService.postDoesNotBelongToUser(postId, (String) session.getAttribute(SESSION_USER_NAME)) &&
                        !(session.getAttribute(SESSION_USER_ROLE).toString().equals(Role.ADMIN.toString()) ||
                                session.getAttribute(SESSION_USER_ROLE).toString().equals(Role.ADMIN.toString()))
                )) {
            return redirectHomePage;
        }

        Post post = postService.findEntityById(postId);
        request.setAttribute(POST_TO_EDIT, post);
        session.setAttribute(SESSION_EDIT_POST_ID, postId);

        return forwardEditPage;
    }
}
