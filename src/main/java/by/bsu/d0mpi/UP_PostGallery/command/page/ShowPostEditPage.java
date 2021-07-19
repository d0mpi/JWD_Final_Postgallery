package by.bsu.d0mpi.UP_PostGallery.command.page;

import by.bsu.d0mpi.UP_PostGallery.command.Command;
import by.bsu.d0mpi.UP_PostGallery.command.CommandRequest;
import by.bsu.d0mpi.UP_PostGallery.command.CommandResponse;
import by.bsu.d0mpi.UP_PostGallery.command.SimpleCommandResponse;
import by.bsu.d0mpi.UP_PostGallery.dao.impl.MySqlPostDao;
import by.bsu.d0mpi.UP_PostGallery.model.Post;
import by.bsu.d0mpi.UP_PostGallery.service.PostService;
import by.bsu.d0mpi.UP_PostGallery.service.impl.SimplePostService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpSession;
import java.util.Objects;

public class ShowPostEditPage implements Command {
    private static final Logger LOGGER = LogManager.getLogger();
    private static volatile ShowPostEditPage instance;

    private final PostService postService;
    private final CommandResponse redirectHomePage;
    private final CommandResponse forwardEditPage;

    public ShowPostEditPage() {
        postService = new SimplePostService();
        redirectHomePage = new SimpleCommandResponse("/controller?command=main_page", true);
        forwardEditPage = new SimpleCommandResponse("/WEB-INF/views/edit.jsp",false);
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
        HttpSession userName = request.getCurrentSession().orElse(null);
        Integer postId = null;
        HttpSession session = request.getCurrentSession().orElse(null);
        try {
            postId = Integer.valueOf(request.getParameter("post_id"));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        if (postId == null || session == null || postService.findEntityById(postId) == null ||
                !postService.doesPostBelongsToAuthor(postId, (String) session.getAttribute("user_name"))) {
            System.out.println("try again");
            return redirectHomePage;
        }

        Post post = postService.findEntityById(postId);
        request.setAttribute("postToEdit", post);
        session.setAttribute("lastEditPostId", postId);

        return forwardEditPage;
    }
}
