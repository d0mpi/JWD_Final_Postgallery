package by.bsu.d0mpi.UP_PostGallery.controller;

import by.bsu.d0mpi.UP_PostGallery.command.Command;
import by.bsu.d0mpi.UP_PostGallery.command.CommandResponse;
import by.bsu.d0mpi.UP_PostGallery.command.SimpleCommandRequest;

import javax.servlet.RequestDispatcher;
import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * the servlet that processes the request received from the client,
 * performs the necessary {@link Command} based on the 'command' parameter in the URL
 * and transmits the necessary response to the server.
 *
 * @author d0mpi
 * @version 1.0
 * @see HttpServlet
 * @see CommandResponse
 * @see by.bsu.d0mpi.UP_PostGallery.command.CommandRequest
 * @see Command
 */
@WebServlet(urlPatterns = "/controller")
@MultipartConfig
public class ApplicationController extends HttpServlet {

    public static final String COMMAND_PARAM_NAME = "command";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);
    }

    @Override
    public void init() throws ServletException {
        super.init();
    }

    /**
     * Parses the command from the url and passes control to it,
     * then executes the received response
     */
    private void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final String commandName = req.getParameter(COMMAND_PARAM_NAME);
        final Command command = Command.withName(commandName);
        final CommandResponse response = command.execute(SimpleCommandRequest.of(req));

        if (!response.doNothing()) {
            if (response.isRedirect()) {
                resp.sendRedirect(response.getPath());
            } else {
                final RequestDispatcher dispatcher = req.getRequestDispatcher(response.getPath());
                dispatcher.forward(req, resp);
            }
        }
    }
}
