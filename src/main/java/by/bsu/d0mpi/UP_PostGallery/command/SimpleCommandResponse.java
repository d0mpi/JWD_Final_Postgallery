package by.bsu.d0mpi.UP_PostGallery.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Default proxy class of {@link javax.servlet.http.HttpServletResponse} allowing you to perform some actions
 * with the request much easier.
 * If you need to redirect the user to another page after processing the request,
 * then set true to {@link SimpleCommandResponse#redirect} flag.
 * If you need to forward the user to another page after processing the request,
 * then set false to {@link SimpleCommandResponse#redirect} flag.
 * If you need to doNothing after processing the request (when actions occur on the js level),
 * then set true to {@link SimpleCommandResponse#doNothing} flag.
 *
 * @author d0mpi
 * @version 1.0
 * @see CommandType
 * @see CommandRequest
 * @see javax.servlet.http.HttpServletResponse
 */
public class SimpleCommandResponse implements CommandResponse {
    private final String path;
    private final boolean redirect;
    private final boolean doNothing;


    /**
     * @param path         the path to which the user should be redirected after successfully processing the request
     * @param redirectFlag If you need to redirect the user to another page after processing the request,
     *                     then set true.
     *                     If you need to forward the user to another page after processing the request,
     *                     then set false.
     */
    public SimpleCommandResponse(String path, boolean redirectFlag) {
        this(path, redirectFlag, false);
    }

    /**
     * @param path         the path to which the user should be redirected after successfully processing the request
     * @param redirectFlag If you need to redirect the user to another page after processing the request,
     *                     then set true.
     *                     If you need to forward the user to another page after processing the request,
     *                     then set false.
     * @param doNothing    If you need to doNothing after processing the request (when actions occur on the js level),
     *                     then set true
     */
    public SimpleCommandResponse(String path, boolean redirectFlag, boolean doNothing) {
        this.path = path;
        this.redirect = redirectFlag;
        this.doNothing = doNothing;
    }

    /**
     * @return response path
     */
    @Override
    public String getPath() {
        return path;
    }

    /**
     * @return redirect value
     */
    @Override
    public boolean isRedirect() {
        return redirect;
    }

    /**
     * @return doNothing value
     */
    @Override
    public boolean doNothing() {
        return doNothing;
    }
}
