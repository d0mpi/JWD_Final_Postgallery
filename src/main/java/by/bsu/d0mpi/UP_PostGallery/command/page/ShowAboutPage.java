package by.bsu.d0mpi.UP_PostGallery.command.page;

import by.bsu.d0mpi.UP_PostGallery.command.Command;
import by.bsu.d0mpi.UP_PostGallery.command.CommandRequest;
import by.bsu.d0mpi.UP_PostGallery.command.CommandResponse;
import by.bsu.d0mpi.UP_PostGallery.command.SimpleCommandResponse;
import by.bsu.d0mpi.UP_PostGallery.command.action.AddPostAction;
import by.bsu.d0mpi.UP_PostGallery.exception.SessionAttributeNotFoundException;
import by.bsu.d0mpi.UP_PostGallery.exception.SessionNotFoundException;
import by.bsu.d0mpi.UP_PostGallery.model.Post;
import by.bsu.d0mpi.UP_PostGallery.model.User;
import by.bsu.d0mpi.UP_PostGallery.service.PostService;
import by.bsu.d0mpi.UP_PostGallery.service.impl.SimpleLikeService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

/**
 * Implementation of the Command interface, which is responsible
 * for the forwarding to the about page.
 * Implements thread-safe Singleton pattern using double checked locking idiom.
 *
 * @author d0mpi
 * @version 1.0
 * @see Command
 * @see CommandResponse
 * @see CommandRequest
 */
public class ShowAboutPage implements Command {
    private static final Logger logger = LogManager.getLogger();
    private static volatile ShowAboutPage instance;

    private  ShowAboutPage() {
    }

    /**
     * Provide a global access point to the instance of the {@link AddPostAction} class.
     *
     * @return the only instance of the {@link AddPostAction} class
     */
    public static ShowAboutPage getInstance() {
        ShowAboutPage localInstance = instance;
        if (localInstance == null) {
            synchronized (ShowAboutPage.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new ShowAboutPage();
                }
            }
        }
        return localInstance;
    }

    /**
     * Forwards user to the about page.
     *
     * @param request the object contains a request received from the client
     * @return an object of the {@link CommandResponse} class with forwarding to the about page.
     */
    @Override
    public CommandResponse execute(CommandRequest request) {
        return new SimpleCommandResponse("WEB-INF/views/about.jsp", false);

    }
}
