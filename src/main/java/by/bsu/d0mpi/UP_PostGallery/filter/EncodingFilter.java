package by.bsu.d0mpi.UP_PostGallery.filter;


import javax.servlet.*;
import java.io.IOException;

public class EncodingFilter implements Filter {

    private String encoding;

    public void init(FilterConfig config) {
        encoding = "UTF-8";
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain next)
            throws IOException, ServletException {
        if (null == request.getCharacterEncoding()) {
            request.setCharacterEncoding(encoding);
        }

        response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");

        next.doFilter(request, response);
    }

    public void destroy() {
    }
}