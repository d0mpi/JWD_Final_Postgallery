package by.bsu.d0mpi.UP_PostGallery.command.page;

import by.bsu.d0mpi.UP_PostGallery.command.Command;
import by.bsu.d0mpi.UP_PostGallery.command.CommandRequest;
import by.bsu.d0mpi.UP_PostGallery.command.CommandResponse;
import by.bsu.d0mpi.UP_PostGallery.command.SimpleCommandResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ShowLoginPage implements Command {
    private static final Logger LOGGER = LogManager.getLogger();
    private static volatile ShowLoginPage instance;

    public static ShowLoginPage getInstance() {
        ShowLoginPage localInstance = instance;
        if (localInstance == null) {
            synchronized (ShowLoginPage.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new ShowLoginPage();
                }
            }
        }
        return localInstance;
    }

    @Override
    public CommandResponse execute(CommandRequest request) {
        return new SimpleCommandResponse("/WEB-INF/views/sign.jsp", false);
    }
}
