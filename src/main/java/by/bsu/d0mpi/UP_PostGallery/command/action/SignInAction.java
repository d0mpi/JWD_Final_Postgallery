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
import java.util.Date;

import static by.bsu.d0mpi.UP_PostGallery.command.action.ChangePasswordAction.REQUEST_ATTRIBUTE_ERROR_TEXT;
import static by.bsu.d0mpi.UP_PostGallery.command.action.RegistrationAction.*;
import static by.bsu.d0mpi.UP_PostGallery.command.page.ShowPostEditPage.SESSION_USER_NAME;
import static by.bsu.d0mpi.UP_PostGallery.command.page.ShowPostEditPage.SESSION_USER_ROLE;

public class SignInAction implements Command {
    private static final Logger logger = LogManager.getLogger();
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
        final String login = request.getParameter(REQUEST_LOGIN_PARAM);
        final String password = request.getParameter(REQUEST_PASSWORD_PARAM);
        final User enteredUser = new User(login, password, new Date());
        if (!userService.canLogIn(enteredUser)) {
            request.setAttribute(REQUEST_ATTRIBUTE_ERROR_TEXT, "User with this data does not exist.\nTry again.");
            return loginPageResponse;
        }
        return addUserInfoToSession(request, login);
    }

    private CommandResponse addUserInfoToSession(CommandRequest request, String login) {
        request.invalidateCurrentSession();
        final HttpSession session = request.createSession();
        final User loggedInUser = userService.findUserByLogin(login).orElse(null);
        if (null != loggedInUser) {
            session.setAttribute(SESSION_USER_NAME, loggedInUser.getLogin());
            session.setAttribute(SESSION_USER_ROLE, loggedInUser.getRole());
            Integer age = Days.daysBetween(new DateTime(loggedInUser.getCreatedDate()), new DateTime()).getDays();
            session.setAttribute(SESSION_USER_AGE, age);
            return homePageResponse;
        } else {
            request.setAttribute(REQUEST_ATTRIBUTE_ERROR_TEXT, "User with this data does not exist!!!");
            return loginPageResponse;
        }
    }
}
