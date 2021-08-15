package by.bsu.d0mpi.UP_PostGallery.command;

import lombok.Getter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

/**
 * Default proxy class of {@link HttpServletRequest} allowing you to perform some actions
 * with the request much easier.
 *
 * @author d0mpi
 * @version 1.0
 * @see CommandType
 * @see CommandRequest
 * @see HttpServletRequest
 * @see HttpSession
 */
public class SimpleCommandRequest implements CommandRequest {

    private static final Logger logger = LogManager.getLogger();

    private final HttpServletRequest request;

    private SimpleCommandRequest(HttpServletRequest request) {
        this.request = request;
    }

    /**
     * Creates a new session object even if it exists
     *
     * @return a new session object
     */
    @Override
    public HttpSession createSession() {
        return request.getSession(true);
    }

    /**
     * @return {@link Optional} object with current session
     */
    @Override
    public Optional<HttpSession> getCurrentSession() {
        return Optional.ofNullable(request.getSession(false));
    }

    /**
     * Invalidates the current session, if it exists
     */
    @Override
    public void invalidateCurrentSession() {
        final HttpSession session = request.getSession(false);
        if (null != session) {
            session.invalidate();
        }
    }

    @Override
    public Object getAttribute(String name) {
        return request.getAttribute(name);
    }

    @Override
    public String getParameter(String name) {
        return new String(request.getParameter(name).getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
    }

    @Override
    public void setAttribute(String name, Object value) {
        request.setAttribute(name, value);
    }

    @Override
    public void setSessionAttribute(String name, Object value) {
        final HttpSession session = request.getSession();
        session.setAttribute(name, value);
    }

    @Override
    public Part getPart(String name) throws ServletException, IOException {
        return request.getPart(name);
    }

    @Override
    public void setCharacterEncoding(String s) {
        try {
            request.setCharacterEncoding(s);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ServletContext getServletContext() {
        return request.getServletContext();
    }

    /**
     * Сhecks whether the required parameter is present in the request
     *
     * @param name parameter name
     * @return true - if parameter with this name exists, otherwise - false
     */
    @Override
    public boolean hasParameter(String name) {
        return request.getParameter(name) != null;
    }

    public Cookie[] getCookies() {
        return request.getCookies();
    }

    /**
     * Replacing the constructor
     *
     * @param request the object contains a request received from the client
     * @return a {@link SimpleCommandRequest} instance corresponding to the request
     */
    public static CommandRequest of(HttpServletRequest request) {
        return new SimpleCommandRequest(request);
    }
}
