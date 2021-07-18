package by.bsu.d0mpi.UP_PostGallery.command.action;

import by.bsu.d0mpi.UP_PostGallery.command.Command;
import by.bsu.d0mpi.UP_PostGallery.command.CommandRequest;
import by.bsu.d0mpi.UP_PostGallery.command.CommandResponse;
import by.bsu.d0mpi.UP_PostGallery.command.SimpleCommandResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpSession;

public class SignOutAction implements Command {
    private static final Logger LOGGER = LogManager.getLogger();
    private static volatile SignOutAction instance;

    public static SignOutAction getInstance() {
        SignOutAction localInstance = instance;
        if (localInstance == null) {
            synchronized (SignOutAction.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new SignOutAction();
                }
            }
        }
        return localInstance;
    }

    @Override
    public CommandResponse execute(CommandRequest request) {
        request.invalidateCurrentSession();
        return new SimpleCommandResponse("/controller?command=main_page", true);
    }
}
