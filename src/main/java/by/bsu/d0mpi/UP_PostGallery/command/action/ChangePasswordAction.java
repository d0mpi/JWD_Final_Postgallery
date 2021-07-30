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

public class ChangePasswordAction implements Command {
    private static final Logger logger = LogManager.getLogger();
    private static volatile ChangePasswordAction instance;

    private final CommandResponse redirectErrorPage;
    private final CommandResponse forwardProfilePage;
    private final UserService userService;

    public ChangePasswordAction() {
        redirectErrorPage = new SimpleCommandResponse("/controller?command=error_page", false);
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
        if (!request.hasParameter("old_password") || !request.hasParameter("new_password")) {
            return redirectErrorPage;
        }
        final String oldPassword = request.getParameter("old_password");
        final String newPassword = request.getParameter("new_password");
        HttpSession session = request.getCurrentSession().orElse(null);
        if (oldPassword.equals(newPassword)) {
            request.setAttribute("error_text", "Passwords match.\nTry again.");
            return forwardProfilePage;
        }
        if (session == null) {
            return redirectErrorPage;
        }

        final String login = (String) session.getAttribute("user_name");
        final Role role = (Role) session.getAttribute("current_role");
        User user = new User(login, oldPassword, role);
        if (!userService.canLogIn(user)) {
            request.setAttribute("error_text", "Wrong password.\nTry again.");
            return forwardProfilePage;
        }
        user.setPassword(newPassword);
        userService.changePassword(user);
        request.setAttribute("error_text", "Successfully.");
        return forwardProfilePage;
    }
}
