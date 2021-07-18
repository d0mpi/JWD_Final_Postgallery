package by.bsu.d0mpi.UP_PostGallery.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

public interface CommandRequest {

    HttpSession createSession();

    Optional<HttpSession> getCurrentSession();

    void invalidateCurrentSession();

    Object getAttribute(String name);

    String getParameter(String name);

    void setAttribute(String name, Object value);

    void setSessionAttribute(String name, Object value);
}
