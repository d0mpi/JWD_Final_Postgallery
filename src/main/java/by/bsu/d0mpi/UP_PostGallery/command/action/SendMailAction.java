package by.bsu.d0mpi.UP_PostGallery.command.action;

import by.bsu.d0mpi.UP_PostGallery.command.Command;
import by.bsu.d0mpi.UP_PostGallery.command.CommandRequest;
import by.bsu.d0mpi.UP_PostGallery.command.CommandResponse;
import by.bsu.d0mpi.UP_PostGallery.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SendMailAction implements Command {
    private static final Logger LOGGER = LogManager.getLogger();
    private static volatile SendMailAction instance;

    public static SendMailAction getInstance() {
        SendMailAction localInstance = instance;
        if (localInstance == null) {
            synchronized (SendMailAction.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new SendMailAction();
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
