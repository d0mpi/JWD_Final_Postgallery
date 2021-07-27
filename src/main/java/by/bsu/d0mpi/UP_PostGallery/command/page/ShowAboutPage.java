package by.bsu.d0mpi.UP_PostGallery.command.page;

import by.bsu.d0mpi.UP_PostGallery.command.Command;
import by.bsu.d0mpi.UP_PostGallery.command.CommandRequest;
import by.bsu.d0mpi.UP_PostGallery.command.CommandResponse;
import by.bsu.d0mpi.UP_PostGallery.command.SimpleCommandResponse;
import by.bsu.d0mpi.UP_PostGallery.service.impl.SimpleLikeService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ShowAboutPage implements Command {
    private static final Logger logger = LogManager.getLogger();
    private static volatile ShowAboutPage instance;

    public static ShowAboutPage getInstance() {
        ShowAboutPage localInstance = instance;
        if (localInstance == null) {
            synchronized (ShowAboutPage.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new ShowAboutPage();
                }
            }
        }
        return localInstance;
    }

    @Override
    public CommandResponse execute(CommandRequest request) {
        return new SimpleCommandResponse("WEB-INF/views/about.jsp", false);

    }
}
