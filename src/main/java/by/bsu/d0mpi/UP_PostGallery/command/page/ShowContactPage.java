package by.bsu.d0mpi.UP_PostGallery.command.page;

import by.bsu.d0mpi.UP_PostGallery.command.Command;
import by.bsu.d0mpi.UP_PostGallery.command.CommandRequest;
import by.bsu.d0mpi.UP_PostGallery.command.CommandResponse;
import by.bsu.d0mpi.UP_PostGallery.command.SimpleCommandResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ShowContactPage implements Command {
    private static final Logger LOGGER = LogManager.getLogger();
    private static volatile ShowContactPage instance;

    public static ShowContactPage getInstance() {
        ShowContactPage localInstance = instance;
        if (localInstance == null) {
            synchronized (ShowContactPage.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new ShowContactPage();
                }
            }
        }
        return localInstance;
    }

    @Override
    public CommandResponse execute(CommandRequest request) {
        return new SimpleCommandResponse("WEB-INF/views/contact.jsp", false);
    }
}
