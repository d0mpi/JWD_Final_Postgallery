package by.bsu.d0mpi.UP_PostGallery.command.page;

import by.bsu.d0mpi.UP_PostGallery.command.Command;
import by.bsu.d0mpi.UP_PostGallery.command.CommandRequest;
import by.bsu.d0mpi.UP_PostGallery.command.CommandResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ShowPostEditPage implements Command {
    private static final Logger LOGGER = LogManager.getLogger();
    private static volatile ShowPostEditPage instance;

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
        return null;
    }
}
