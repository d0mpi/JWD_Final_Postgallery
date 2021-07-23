package by.bsu.d0mpi.UP_PostGallery.command.action;

import by.bsu.d0mpi.UP_PostGallery.command.Command;
import by.bsu.d0mpi.UP_PostGallery.command.CommandRequest;
import by.bsu.d0mpi.UP_PostGallery.command.CommandResponse;
import by.bsu.d0mpi.UP_PostGallery.command.SimpleCommandResponse;
import by.bsu.d0mpi.UP_PostGallery.model.User;
import by.bsu.d0mpi.UP_PostGallery.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.Days;

import javax.servlet.http.HttpSession;

public class RegistrationAction implements Command {

    private static final Logger LOGGER = LogManager.getLogger();
    private static volatile RegistrationAction instance;

    private final UserService userService;
    private final CommandResponse registrationPageResponse;
    private final CommandResponse homePageResponse;

    public static RegistrationAction getInstance() {
        RegistrationAction localInstance = instance;
        if (localInstance == null) {
            synchronized (RegistrationAction.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new RegistrationAction();
                }
            }
        }
        return localInstance;
    }

    public RegistrationAction() {
        registrationPageResponse = new SimpleCommandResponse("/WEB-INF/views/registration.jsp", false);
        homePageResponse = new SimpleCommandResponse("/controller?command=main_page",true);
        userService = UserService.simple();
    }

    @Override
    public CommandResponse execute(CommandRequest request) {
        final String login = request.getParameter("login");
        final String password = request.getParameter("password");
        final User enteredUser = new User(login, password);
        if (userService.isLoginPresented(login)) {
            request.setAttribute("error_text", "User with this login is already exist. Try again.");
            return registrationPageResponse;
        }
        userService.createEntity(enteredUser);
        return addUserInfoToSession(request, login);
    }

    private CommandResponse addUserInfoToSession(CommandRequest request, String login) {
        request.invalidateCurrentSession();
        final HttpSession session = request.createSession();
        final User loggedInUser = userService.findUserByLogin(login).orElse(null);
        if (null != loggedInUser) {
            session.setAttribute("user_name", loggedInUser.getLogin());
            session.setAttribute("current_role", loggedInUser.getRole());
            Integer age = Days.daysBetween(new DateTime(loggedInUser.getRegistrationDate()), new DateTime()).getDays();
            session.setAttribute("user_age", age);
            return homePageResponse;
        } else {
            request.setAttribute("error_text", "User with this login is already exist. Try again.");
            return registrationPageResponse;
        }
    }
}
