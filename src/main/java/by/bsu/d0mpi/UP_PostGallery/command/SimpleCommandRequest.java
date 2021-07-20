package by.bsu.d0mpi.UP_PostGallery.command;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

public class SimpleCommandRequest implements CommandRequest{

    private static final Logger LOGGER = LogManager.getLogger();
    private final HttpServletRequest request;

    private SimpleCommandRequest(HttpServletRequest request) {
        this.request = request;
    }
    
    @Override
    public HttpSession createSession() {
        return request.getSession(true);
    }

    @Override
    public Optional<HttpSession> getCurrentSession() {
        return Optional.ofNullable(request.getSession(false));
    }

    @Override
    public void invalidateCurrentSession() {
        final HttpSession session = request.getSession(false);
        if(null != session) {
            session.invalidate();
        }
    }

    @Override
    public Object getAttribute(String name) {
        return request.getAttribute(name);
    }

    @Override
    public String getParameter(String name) {
        return request.getParameter(name);
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
    public boolean hasParameter(String name) {
        return request.getParameter(name) != null;
    }


    public static CommandRequest of(HttpServletRequest request) {
        return new SimpleCommandRequest(request);
    }
}