package by.bsu.d0mpi.UP_PostGallery.command.page;

import by.bsu.d0mpi.UP_PostGallery.command.Command;
import by.bsu.d0mpi.UP_PostGallery.command.CommandRequest;
import by.bsu.d0mpi.UP_PostGallery.command.CommandResponse;
import by.bsu.d0mpi.UP_PostGallery.command.SimpleCommandResponse;
import by.bsu.d0mpi.UP_PostGallery.command.action.AddPostAction;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Implementation of the Command interface, which is responsible
 * for the forwarding to the contact page.
 * Implements thread-safe Singleton pattern using double checked locking idiom.
 *
 * @author d0mpi
 * @version 1.0
 * @see Command
 * @see CommandResponse
 * @see CommandRequest
 */
public class ShowContactPage implements Command {
    private static final Logger logger = LogManager.getLogger();
    private static volatile ShowContactPage instance;

    private ShowContactPage() {
    }

    /**
     * Provide a global access point to the instance of the {@link ShowContactPage} class.
     *
     * @return the only instance of the {@link ShowContactPage} class
     */
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

    /**
     * Forwards user to the contact page.
     *
     * @param request the object contains a request received from the client
     * @return an object of the {@link CommandResponse} class with forwarding to the contact page.
     */
    @Override
    public CommandResponse execute(CommandRequest request) {
        return new SimpleCommandResponse("WEB-INF/views/contact.jsp", false);
    }
}
