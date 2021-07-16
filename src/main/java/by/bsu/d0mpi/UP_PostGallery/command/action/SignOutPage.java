package by.bsu.d0mpi.UP_PostGallery.command.action;

import by.bsu.d0mpi.UP_PostGallery.command.Command;
import by.bsu.d0mpi.UP_PostGallery.command.CommandRequest;
import by.bsu.d0mpi.UP_PostGallery.command.CommandResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SignOutPage implements Command {
    private static final Logger LOGGER = LogManager.getLogger();
    private static volatile SignOutPage instance;

    public static SignOutPage getInstance() {
        SignOutPage localInstance = instance;
        if (localInstance == null) {
            synchronized (SignOutPage.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new SignOutPage();
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
