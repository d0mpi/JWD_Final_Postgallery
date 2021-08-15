package by.bsu.d0mpi.UP_PostGallery.command.action;

import by.bsu.d0mpi.UP_PostGallery.command.Command;
import by.bsu.d0mpi.UP_PostGallery.command.CommandRequest;
import by.bsu.d0mpi.UP_PostGallery.command.CommandResponse;
import by.bsu.d0mpi.UP_PostGallery.command.SimpleCommandResponse;
import by.bsu.d0mpi.UP_PostGallery.model.Role;
import by.bsu.d0mpi.UP_PostGallery.model.User;
import by.bsu.d0mpi.UP_PostGallery.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpSession;

import static by.bsu.d0mpi.UP_PostGallery.command.page.ShowPostEditPage.SESSION_USER_NAME;
import static by.bsu.d0mpi.UP_PostGallery.command.page.ShowPostEditPage.SESSION_USER_ROLE;

public class ChangePasswordAction implements Command {
    private static final Logger logger = LogManager.getLogger();
    public static final String REQUEST_OLD_PASSWORD_PARAM = "old_password";
    public static final String REQUEST_NEW_PASSWORD_PARAM = "new_password";
    public static final String REQUEST_ATTRIBUTE_ERROR_TEXT = "error_text";
    private static volatile ChangePasswordAction instance;

    private final CommandResponse redirectErrorPage;
    private final CommandResponse forwardProfilePage;
    private final CommandResponse redirectProfilePage;
    private final UserService userService;

    public ChangePasswordAction() {
        redirectErrorPage = new SimpleCommandResponse("/controller?command=error_page", false);
        redirectProfilePage = new SimpleCommandResponse("/controller?command=user_profile_page&error_text='Successfully!!!'", true);
        forwardProfilePage = new SimpleCommandResponse("/WEB-INF/views/profile.jsp", false);
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
        if (!request.hasParameter(REQUEST_OLD_PASSWORD_PARAM) || !request.hasParameter(REQUEST_NEW_PASSWORD_PARAM)) {
            return redirectErrorPage;
        }

        final String oldPassword = request.getParameter(REQUEST_OLD_PASSWORD_PARAM);
        final String newPassword = request.getParameter(REQUEST_NEW_PASSWORD_PARAM);
        System.out.println(oldPassword);
        HttpSession session = request.getCurrentSession().orElse(null);
        if (oldPassword.equals(newPassword)) {
            request.setAttribute(REQUEST_ATTRIBUTE_ERROR_TEXT, "Passwords match.\nTry again.");
            return forwardProfilePage;
        }
        if (session == null) {
            return redirectErrorPage;
        }

        final String login = (String) session.getAttribute(SESSION_USER_NAME);
        final Role role = (Role) session.getAttribute(SESSION_USER_ROLE);
        User user = new User(login, oldPassword, role);
        if (!userService.canLogIn(user)) {
            request.setAttribute(REQUEST_ATTRIBUTE_ERROR_TEXT, "Wrong password.\nTry again.");
            return forwardProfilePage;
        }
        user.setPassword(newPassword);
        userService.changePassword(user);
        request.setAttribute(REQUEST_ATTRIBUTE_ERROR_TEXT, "Successfully.");
        return redirectProfilePage;
    }
}
