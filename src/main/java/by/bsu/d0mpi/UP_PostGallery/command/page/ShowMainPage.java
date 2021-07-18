package by.bsu.d0mpi.UP_PostGallery.command.page;

import by.bsu.d0mpi.UP_PostGallery.command.Command;
import by.bsu.d0mpi.UP_PostGallery.command.CommandRequest;
import by.bsu.d0mpi.UP_PostGallery.command.CommandResponse;
import by.bsu.d0mpi.UP_PostGallery.command.SimpleCommandResponse;
import by.bsu.d0mpi.UP_PostGallery.dao.impl.MySqlPostDao;
import by.bsu.d0mpi.UP_PostGallery.model.Post;
import by.bsu.d0mpi.UP_PostGallery.service.PostService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.RequestDispatcher;
import java.util.List;

public class ShowMainPage implements Command {
    private static final Logger LOGGER = LogManager.getLogger();
    private static volatile ShowMainPage instance;

    public static ShowMainPage getInstance() {
        ShowMainPage localInstance = instance;
        if (localInstance == null) {
            synchronized (ShowMainPage.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new ShowMainPage();
                }
            }
        }
        return localInstance;
    }

    @Override
    public CommandResponse execute(CommandRequest request) {
        List<Post> postArrayList = PostService.simple().findAll();
        request.setAttribute("postList", postArrayList);
        return new SimpleCommandResponse("WEB-INF/views/index.jsp", false);
    }
}
