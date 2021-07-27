package by.bsu.d0mpi.UP_PostGallery.command.page;

import by.bsu.d0mpi.UP_PostGallery.command.Command;
import by.bsu.d0mpi.UP_PostGallery.command.CommandRequest;
import by.bsu.d0mpi.UP_PostGallery.command.CommandResponse;
import by.bsu.d0mpi.UP_PostGallery.command.SimpleCommandResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ShowRegistrationPage implements Command {
    private static final Logger logger = LogManager.getLogger();
    private static volatile ShowRegistrationPage instance;

    public static ShowRegistrationPage getInstance() {
        ShowRegistrationPage localInstance = instance;
        if (localInstance == null) {
            synchronized (ShowRegistrationPage.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new ShowRegistrationPage();
                }
            }
        }
        return localInstance;
    }

    @Override
    public CommandResponse execute(CommandRequest request) {
        return new SimpleCommandResponse("WEB-INF/views/registration.jsp", false);

    }
}
