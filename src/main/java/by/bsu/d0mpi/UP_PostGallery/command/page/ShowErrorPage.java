package by.bsu.d0mpi.UP_PostGallery.command.page;

import by.bsu.d0mpi.UP_PostGallery.command.Command;
import by.bsu.d0mpi.UP_PostGallery.command.CommandRequest;
import by.bsu.d0mpi.UP_PostGallery.command.CommandResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ShowErrorPage implements Command {
    private static final Logger LOGGER = LogManager.getLogger();
    private static volatile ShowErrorPage instance;

    public static ShowErrorPage getInstance() {
        ShowErrorPage localInstance = instance;
        if (localInstance == null) {
            synchronized (ShowErrorPage.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new ShowErrorPage();
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
