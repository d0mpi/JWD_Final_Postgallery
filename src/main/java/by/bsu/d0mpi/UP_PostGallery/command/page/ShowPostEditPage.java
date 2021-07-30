package by.bsu.d0mpi.UP_PostGallery.command.page;

import by.bsu.d0mpi.UP_PostGallery.command.Command;
import by.bsu.d0mpi.UP_PostGallery.command.CommandRequest;
import by.bsu.d0mpi.UP_PostGallery.command.CommandResponse;
import by.bsu.d0mpi.UP_PostGallery.command.SimpleCommandResponse;
import by.bsu.d0mpi.UP_PostGallery.model.Post;
import by.bsu.d0mpi.UP_PostGallery.service.PostService;
import by.bsu.d0mpi.UP_PostGallery.service.impl.SimplePostService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpSession;

public class ShowPostEditPage implements Command {
    private static final Logger logger = LogManager.getLogger();
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
            postId = Integer.valueOf(request.getParameter("post_id"));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        if (postId == null ||
                session == null ||
                postService.findEntityById(postId) == null ||
                (!postService.doesPostBelongsToAuthor(postId, (String) session.getAttribute("user_name")) &&
                        !(session.getAttribute("current_role").toString().equals("ADMIN") ||
                                session.getAttribute("current_role").toString().equals("MODERATOR"))
                )) {
            return redirectHomePage;
        }

        Post post = postService.findEntityById(postId);
        request.setAttribute("postToEdit", post);
        session.setAttribute("lastEditPostId", postId);

        return forwardEditPage;
    }
}
