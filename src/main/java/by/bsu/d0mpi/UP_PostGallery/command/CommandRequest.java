package by.bsu.d0mpi.UP_PostGallery.command;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import java.io.IOException;
import java.util.Optional;

public interface CommandRequest {

    HttpSession createSession();

    Optional<HttpSession> getCurrentSession();

    void invalidateCurrentSession();

    Object getAttribute(String name);

    String getParameter(String name);

    void setAttribute(String name, Object value);

    void setSessionAttribute(String name, Object value);

    ServletContext getServletContext();

    boolean hasParameter(String name);

    Cookie[] getCookies();

    Part getPart(String name) throws ServletException, IOException;
}
