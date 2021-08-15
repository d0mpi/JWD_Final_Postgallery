package by.bsu.d0mpi.UP_PostGallery.filter;


import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * Filter sets UTF-8 as the request and response character encoding,
 * text/html as content type.
 *
 * @author d0mpi
 * @version 1.0
 */
@WebFilter(urlPatterns = "/controller")
public class EncodingFilter implements Filter {

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain next)
            throws IOException, ServletException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        next.doFilter(request, response);
    }

    public void destroy() {
    }
}