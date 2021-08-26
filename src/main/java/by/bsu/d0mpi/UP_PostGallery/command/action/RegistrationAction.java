package by.bsu.d0mpi.UP_PostGallery.command.action;

import by.bsu.d0mpi.UP_PostGallery.command.Command;
import by.bsu.d0mpi.UP_PostGallery.command.CommandRequest;
import by.bsu.d0mpi.UP_PostGallery.command.CommandResponse;
import by.bsu.d0mpi.UP_PostGallery.command.SimpleCommandResponse;
import by.bsu.d0mpi.UP_PostGallery.model.Post;
import by.bsu.d0mpi.UP_PostGallery.model.User;
import by.bsu.d0mpi.UP_PostGallery.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.Days;

import javax.servlet.http.HttpSession;
import java.util.Date;

import static by.bsu.d0mpi.UP_PostGallery.command.action.ChangePasswordAction.REQUEST_ATTRIBUTE_ERROR_TEXT;
import static by.bsu.d0mpi.UP_PostGallery.command.page.ShowPostEditPage.SESSION_USER_NAME;
import static by.bsu.d0mpi.UP_PostGallery.command.page.ShowPostEditPage.SESSION_USER_ROLE;

/**
 * Implementation of the Command interface, which is responsible
 * for the registration of the {@link User}.
 * Implements thread-safe Singleton pattern using double checked locking idiom.
 *
 * @author d0mpi
 * @version 1.0
 * @see Command
 * @see CommandResponse
 * @see UserService
 */
public class RegistrationAction implements Command {

    private static final Logger logger = LogManager.getLogger();

    public static final String SESSION_USER_AGE = "user_age";
    public static final String REQUEST_LOGIN_PARAM = "login";
    public static final String REQUEST_PASSWORD_PARAM = "password";
    private static volatile RegistrationAction instance;

    private final UserService userService;
    private final CommandResponse registrationPageResponse;
    private final CommandResponse homePageResponse;


    private RegistrationAction() {
        registrationPageResponse = new SimpleCommandResponse("/WEB-INF/views/registration.jsp", false);
        homePageResponse = new SimpleCommandResponse("/controller?command=main_page",true);
        userService = UserService.simple();
    }

    /**
     * Provide a global access point to the instance of the {@link RegistrationAction} class.
     *
     * @return the only instance of the {@link RegistrationAction} class
     */
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

    /**
     * Parses user data from the received request.
     * Checks whether there is a user with the same username in the database.
     * If there is one, then you need to enter the data again, otherwise,
     * the user will be registered and redirected to the main page.
     *
     * @param request the object contains a request received from the client
     * @return an object of the {@link CommandResponse} class with redirection to the main page
     * after successful registration and to the registration page if something goes wrong.
     */
    @Override
    public CommandResponse execute(CommandRequest request) {
        final String login = request.getParameter(REQUEST_LOGIN_PARAM);
        final String password = request.getParameter(REQUEST_PASSWORD_PARAM);
        final User enteredUser = new User(login, password, new Date(System.currentTimeMillis()));
        if (userService.isLoginPresented(login)) {
            request.setAttribute(REQUEST_ATTRIBUTE_ERROR_TEXT, "User with this login is already exist. Try again.");
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
            session.setAttribute(SESSION_USER_NAME, loggedInUser.getLogin());
            session.setAttribute(SESSION_USER_ROLE, loggedInUser.getRole());
            Integer age = Days.daysBetween(new DateTime(loggedInUser.getCreatedDate()), new DateTime()).getDays();
            session.setAttribute(SESSION_USER_AGE, age);
            return homePageResponse;
        } else {
            request.setAttribute(REQUEST_ATTRIBUTE_ERROR_TEXT, "User with this login is already exist. Try again.");
            return registrationPageResponse;
        }
    }
}
