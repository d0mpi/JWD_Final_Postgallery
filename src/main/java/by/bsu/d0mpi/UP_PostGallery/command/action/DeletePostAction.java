package by.bsu.d0mpi.UP_PostGallery.command.action;

import by.bsu.d0mpi.UP_PostGallery.command.Command;
import by.bsu.d0mpi.UP_PostGallery.command.CommandRequest;
import by.bsu.d0mpi.UP_PostGallery.command.CommandResponse;
import by.bsu.d0mpi.UP_PostGallery.command.SimpleCommandResponse;
import by.bsu.d0mpi.UP_PostGallery.service.PostService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DeletePostAction implements Command {
    private static final Logger logger = LogManager.getLogger();
    private static volatile DeletePostAction instance;

    private final CommandResponse redirectHomePage;
    private final PostService postService;

    public DeletePostAction() {
        redirectHomePage = new SimpleCommandResponse("/controller?command=main_page", false);
        postService = PostService.simple();
    }

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

    @Override
    public CommandResponse execute(CommandRequest request) {
        Integer postId = Integer.valueOf(request.getParameter("post_id"));
        postService.delete(postId);
        return redirectHomePage;
    }
}
