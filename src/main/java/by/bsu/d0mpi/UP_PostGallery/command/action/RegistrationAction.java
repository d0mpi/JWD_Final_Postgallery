package by.bsu.d0mpi.UP_PostGallery.command.action;

import by.bsu.d0mpi.UP_PostGallery.command.Command;
import by.bsu.d0mpi.UP_PostGallery.command.CommandRequest;
import by.bsu.d0mpi.UP_PostGallery.command.CommandResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RegistrationAction implements Command {
    private static final Logger LOGGER = LogManager.getLogger();
    private static volatile RegistrationAction instance;

    public static RegistrationAction getInstance() {
        RegistrationAction localInstance = instance;
        if (localInstance == null) {
            synchronized (RegistrationAction.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new RegistrationAction();
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
