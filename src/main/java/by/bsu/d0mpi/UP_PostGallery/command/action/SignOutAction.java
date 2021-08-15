package by.bsu.d0mpi.UP_PostGallery.command.action;

import by.bsu.d0mpi.UP_PostGallery.command.Command;
import by.bsu.d0mpi.UP_PostGallery.command.CommandRequest;
import by.bsu.d0mpi.UP_PostGallery.command.CommandResponse;
import by.bsu.d0mpi.UP_PostGallery.command.SimpleCommandResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpSession;

/**
 * Implementation of the Command interface, which is responsible
 * for signing out user.
 * Implements thread-safe Singleton pattern using double checked locking idiom.
 *
 * @author d0mpi
 * @version 1.0
 * @see Command
 * @see CommandResponse
 * @see CommandRequest
 */
public class SignOutAction implements Command {
    private static final Logger logger = LogManager.getLogger();

    private final CommandResponse redirectHomePage;
    private static volatile SignOutAction instance;

    private SignOutAction() {
        redirectHomePage = new SimpleCommandResponse("/controller?command=main_page", true);
    }

    /**
     * Provide a global access point to the instance of the {@link SignOutAction} class.
     *
     * @return the only instance of the {@link SignOutAction} class
     */
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

    /**
     * Invalidate current session and ensures that the user logs out of the system.
     *
     * @param request the object contains a request received from the client
     * @return an object of the {@link CommandResponse} class with redirection to the main page
     * after successful signing out.
     */
    @Override
    public CommandResponse execute(CommandRequest request) {
        request.invalidateCurrentSession();
        return redirectHomePage;
    }
}
