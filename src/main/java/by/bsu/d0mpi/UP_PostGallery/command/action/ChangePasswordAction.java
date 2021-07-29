package by.bsu.d0mpi.UP_PostGallery.command.action;

import by.bsu.d0mpi.UP_PostGallery.command.Command;
import by.bsu.d0mpi.UP_PostGallery.command.CommandRequest;
import by.bsu.d0mpi.UP_PostGallery.command.CommandResponse;
import by.bsu.d0mpi.UP_PostGallery.command.SimpleCommandResponse;
import by.bsu.d0mpi.UP_PostGallery.service.PostService;
import by.bsu.d0mpi.UP_PostGallery.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ChangePasswordAction implements Command {
    private static final Logger logger = LogManager.getLogger();
    private static volatile ChangePasswordAction instance;

    private final CommandResponse redirectHomePage;
    private final UserService userService;

    public ChangePasswordAction() {
        redirectHomePage = new SimpleCommandResponse("/controller?command=main_page", false);
        userService = UserService.simple();
    }

    public static ChangePasswordAction getInstance() {
        ChangePasswordAction localInstance = instance;
        if (localInstance == null) {
            synchronized (ChangePasswordAction.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new ChangePasswordAction();
                }
            }
        }
        return localInstance;
    }

    @Override
    public CommandResponse execute(CommandRequest request) {
        return redirectHomePage;
    }
}
