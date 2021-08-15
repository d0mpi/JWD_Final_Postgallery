package by.bsu.d0mpi.UP_PostGallery.command.page;

import by.bsu.d0mpi.UP_PostGallery.command.Command;
import by.bsu.d0mpi.UP_PostGallery.command.CommandRequest;
import by.bsu.d0mpi.UP_PostGallery.command.CommandResponse;
import by.bsu.d0mpi.UP_PostGallery.command.SimpleCommandResponse;
import by.bsu.d0mpi.UP_PostGallery.command.action.AddPostAction;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.RequestDispatcher;

/**
 * Implementation of the Command interface, which is responsible
 * for the forwarding to the error page.
 * Implements thread-safe Singleton pattern using double checked locking idiom.
 *
 * @author d0mpi
 * @version 1.0
 * @see Command
 * @see CommandResponse
 * @see CommandRequest
 */
public class ShowErrorPage implements Command {
    private static final Logger logger = LogManager.getLogger();
    private static volatile ShowErrorPage instance;

    private final CommandResponse forwardErrorPage;

    private ShowErrorPage() {
        forwardErrorPage = new SimpleCommandResponse("/WEB-INF/views/error.jsp", false);
    }

    /**
     * Provide a global access point to the instance of the {@link ShowErrorPage} class.
     *
     * @return the only instance of the {@link ShowErrorPage} class
     */
    public static ShowErrorPage getInstance() {
        ShowErrorPage localInstance = instance;
        if (localInstance == null) {
            synchronized (ShowErrorPage.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new ShowErrorPage();
                }
            }
        }
        return localInstance;
    }

    /**
     * Parses error code or exception and forwards user to the error page.
     *
     * @param request the object contains a request received from the client
     * @return an object of the {@link CommandResponse} class with forwarding to the error page.
     */
    @Override
    public CommandResponse execute(CommandRequest request) {
        Throwable throwable = (Throwable)
                request.getAttribute("javax.servlet.error.exception");
        Integer statusCode = (Integer)
                request.getAttribute("javax.servlet.error.status_code");
        String servletName = (String)
                request.getAttribute("javax.servlet.error.servlet_name");
        request.setAttribute("status_code", statusCode == null ? -1 : statusCode);
        request.setAttribute("throwable", throwable);
        return forwardErrorPage;
    }
}
