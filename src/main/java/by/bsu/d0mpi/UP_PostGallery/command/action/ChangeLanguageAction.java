package by.bsu.d0mpi.UP_PostGallery.command.action;

import by.bsu.d0mpi.UP_PostGallery.command.Command;
import by.bsu.d0mpi.UP_PostGallery.command.CommandRequest;
import by.bsu.d0mpi.UP_PostGallery.command.CommandResponse;
import by.bsu.d0mpi.UP_PostGallery.command.SimpleCommandResponse;
import by.bsu.d0mpi.UP_PostGallery.service.PostService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.Cookie;

public class ChangeLanguageAction implements Command {
    private static final Logger logger = LogManager.getLogger();
    public static final String REQUEST_LANGUAGE_PARAM = "language-select";
    public static final String LANGUAGE_COOKIE_NAME = "language";
    private static volatile ChangeLanguageAction instance;

    private final CommandResponse redirectHomePage;

    public ChangeLanguageAction() {
        redirectHomePage = new SimpleCommandResponse("/controller?command=main_page", true);
    }

    public static ChangeLanguageAction getInstance() {
        ChangeLanguageAction localInstance = instance;
        if (localInstance == null) {
            synchronized (ChangeLanguageAction.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new ChangeLanguageAction();
                }
            }
        }
        return localInstance;
    }

    @Override
    public CommandResponse execute(CommandRequest request) {
        if (request.getParameter(REQUEST_LANGUAGE_PARAM) != null) {
            String languageAbbr;
            switch (request.getParameter(REQUEST_LANGUAGE_PARAM)) {
                case ("RU"):
                    languageAbbr = "ru_BY";
                    break;
                case ("EN"):
                    languageAbbr = "en_US";
                    break;
                case ("CN"):
                    languageAbbr = "zh_CN";
                    break;
                default:
                    languageAbbr = "en_US";
                    break;
            }
            new Cookie(LANGUAGE_COOKIE_NAME, languageAbbr);
        } else {
            Cookie[] cookies = request.getCookies();
            if (cookies == null || cookies.length == 0) {
                new Cookie(LANGUAGE_COOKIE_NAME, "en_US");
            } else {
                boolean notPresented = true;
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals(LANGUAGE_COOKIE_NAME)) notPresented = false;
                }
                if (notPresented) {
                    new Cookie(LANGUAGE_COOKIE_NAME, "en_US");
                }
            }
        }
        return redirectHomePage;
    }
}
