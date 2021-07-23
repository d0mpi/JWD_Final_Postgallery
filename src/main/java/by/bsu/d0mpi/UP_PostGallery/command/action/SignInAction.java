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

public class SignInAction implements Command {
    private static final Logger LOGGER = LogManager.getLogger();
    private static volatile SignInAction instance;

    private final UserService userService;
    private final CommandResponse loginPageResponse;
    private final CommandResponse homePageResponse;

    public static SignInAction getInstance() {
        SignInAction localInstance = instance;
        if (localInstance == null) {
            synchronized (SignInAction.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new SignInAction();
                }
            }
        }
        return localInstance;
    }

    private SignInAction() {
        loginPageResponse = new SimpleCommandResponse("/WEB-INF/views/sign.jsp", false);
        homePageResponse = new SimpleCommandResponse("/controller?command=main_page", true);
        userService = UserService.simple();
    }

    @Override
    public CommandResponse execute(CommandRequest request) {
        final String login = request.getParameter("login");
        final String password = request.getParameter("password");
        final User enteredUser = new User(login, password);
        if (!userService.canLogIn(enteredUser)) {
            request.setAttribute("error_text", "User with this data does not exist.\nTry again.");
            return loginPageResponse;
        }
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
            request.setAttribute("error_text", "User with this data does not exist!!!");
            return loginPageResponse;
        }
    }
}
