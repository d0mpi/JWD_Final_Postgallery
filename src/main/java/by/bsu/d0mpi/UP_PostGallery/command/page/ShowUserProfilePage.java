package by.bsu.d0mpi.UP_PostGallery.command.page;

import by.bsu.d0mpi.UP_PostGallery.command.Command;
import by.bsu.d0mpi.UP_PostGallery.command.CommandRequest;
import by.bsu.d0mpi.UP_PostGallery.command.CommandResponse;
import by.bsu.d0mpi.UP_PostGallery.command.SimpleCommandResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ShowUserProfilePage implements Command {
    private static final Logger LOGGER = LogManager.getLogger();
    private static volatile ShowUserProfilePage instance;

    private final CommandResponse forwardProfilePage;

    public ShowUserProfilePage() {
        forwardProfilePage = new SimpleCommandResponse("/WEB-INF/views/profile.jsp",false);
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
        return forwardProfilePage;
    }
}
