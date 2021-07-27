package by.bsu.d0mpi.UP_PostGallery.command.page;

import by.bsu.d0mpi.UP_PostGallery.command.Command;
import by.bsu.d0mpi.UP_PostGallery.command.CommandRequest;
import by.bsu.d0mpi.UP_PostGallery.command.CommandResponse;
import by.bsu.d0mpi.UP_PostGallery.command.SimpleCommandResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.RequestDispatcher;

public class ShowErrorPage implements Command {
    private static final Logger logger = LogManager.getLogger();
    private static volatile ShowErrorPage instance;

    private final CommandResponse forwardErrorPage;

    public ShowErrorPage() {
        forwardErrorPage = new SimpleCommandResponse("/WEB-INF/views/error.jsp", false);
    }

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
