package by.bsu.d0mpi.UP_PostGallery.command.action;

import by.bsu.d0mpi.UP_PostGallery.command.Command;
import by.bsu.d0mpi.UP_PostGallery.command.CommandRequest;
import by.bsu.d0mpi.UP_PostGallery.command.CommandResponse;
import by.bsu.d0mpi.UP_PostGallery.command.SimpleCommandResponse;
import by.bsu.d0mpi.UP_PostGallery.exception.SessionNotFoundException;
import by.bsu.d0mpi.UP_PostGallery.model.Role;
import by.bsu.d0mpi.UP_PostGallery.model.User;
import by.bsu.d0mpi.UP_PostGallery.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpSession;

import static by.bsu.d0mpi.UP_PostGallery.command.page.ShowPostEditPage.SESSION_USER_NAME;
import static by.bsu.d0mpi.UP_PostGallery.command.page.ShowPostEditPage.SESSION_USER_ROLE;

/**
 * Implementation of the Command interface, which is responsible
 * for changing the user's password.
 * Implements thread-safe Singleton pattern using double checked locking idiom.
 *
 * @author d0mpi
 * @version 1.0
 * @see Command
 * @see CommandResponse
 * @see UserService
 * @see HttpSession
 * @see User
 */
public class ChangePasswordAction implements Command {
    private static final Logger logger = LogManager.getLogger();

    public static final String REQUEST_OLD_PASSWORD_PARAM = "old_password";
    public static final String REQUEST_NEW_PASSWORD_PARAM = "new_password";
    public static final String REQUEST_ATTRIBUTE_ERROR_TEXT = "error_text";
    private static volatile ChangePasswordAction instance;

    private final CommandResponse forwardProfilePage;
    private final CommandResponse redirectProfilePage;
    private final UserService userService;

    private ChangePasswordAction() {
        redirectProfilePage = new SimpleCommandResponse("/controller?command=user_profile_page", true);
        forwardProfilePage = new SimpleCommandResponse("/WEB-INF/views/profile.jsp", false);
        userService = UserService.simple();
    }

    /**
     * Provide a global access point to the instance of the {@link ChangePasswordAction} class.
     *
     * @return the only instance of the {@link ChangePasswordAction} class
     */
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

    /**
     * Parses passwords data received from the request, verifies the correctness of the entered data
     * and changes password in the database, if everything is correct.
     *
     * @param request the object contains a request received from the client
     * @return an object of the {@link CommandResponse} class with redirection to the profile page
     * after successful or unsuccessful changing the password.
     */
    @Override
    public CommandResponse execute(CommandRequest request) {
        if (!request.hasParameter(REQUEST_OLD_PASSWORD_PARAM) || !request.hasParameter(REQUEST_NEW_PASSWORD_PARAM)) {
            return forwardProfilePage;
        }
        final String oldPassword = request.getParameter(REQUEST_OLD_PASSWORD_PARAM);
        final String newPassword = request.getParameter(REQUEST_NEW_PASSWORD_PARAM);
        if (oldPassword.equals(newPassword)) {
            request.setAttribute(REQUEST_ATTRIBUTE_ERROR_TEXT, "Passwords match.\nTry again.");
            return forwardProfilePage;
        }

        User user = null;
        try {
            user = createAndVerifyUserWithNewPassword(request, oldPassword, newPassword);
        } catch (SessionNotFoundException e) {
            logger.error("Session was not found!");
        }
        if (user == null)
            return forwardProfilePage;

        userService.changePassword(user);
        request.setAttribute(REQUEST_ATTRIBUTE_ERROR_TEXT, "Successfully.");
        return redirectProfilePage;
    }

    private User createAndVerifyUserWithNewPassword(CommandRequest request, String oldPassword, String newPassword) throws SessionNotFoundException {
        HttpSession session = request.getCurrentSession().orElseThrow(SessionNotFoundException::new);

        final String login = (String) session.getAttribute(SESSION_USER_NAME);
        final Role role = (Role) session.getAttribute(SESSION_USER_ROLE);
        User user = new User(login, oldPassword, role);
        if (!userService.canLogIn(user)) {
            request.setAttribute(REQUEST_ATTRIBUTE_ERROR_TEXT, "Wrong password.\nTry again.");
            return null;
        }
        user.setPassword(newPassword);
        return user;
    }
}
