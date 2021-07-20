package by.bsu.d0mpi.UP_PostGallery.command.action;

import by.bsu.d0mpi.UP_PostGallery.command.Command;
import by.bsu.d0mpi.UP_PostGallery.command.CommandRequest;
import by.bsu.d0mpi.UP_PostGallery.command.CommandResponse;
import by.bsu.d0mpi.UP_PostGallery.command.SimpleCommandResponse;
import by.bsu.d0mpi.UP_PostGallery.service.PostService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.Cookie;

public class ChangeLanguageAction implements Command{
    private static final Logger LOGGER = LogManager.getLogger();
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
        if (request.getParameter("language-select") != null) {
            String languageAbbr;
            switch (request.getParameter("language-select")) {
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
            Cookie cookie = new Cookie("language", languageAbbr);
        } else {
            Cookie[] cookies = request.getCookies();
            if (cookies == null || cookies.length == 0) {
                Cookie cookie = new Cookie("language", "en_US");
            } else {
                boolean notPresented = true;
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals("language")) notPresented = false;
                }
                if (notPresented) {
                    Cookie cookie = new Cookie("language", "en_US");
                }
            }
        }
        return redirectHomePage;
    }
}
