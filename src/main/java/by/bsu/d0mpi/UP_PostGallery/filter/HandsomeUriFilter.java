package by.bsu.d0mpi.UP_PostGallery.filter;

import by.bsu.d0mpi.UP_PostGallery.command.CommandType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class HandsomeUriFilter implements Filter {
    private static final Logger LOGGER = LogManager.getLogger();

    private static Map<String, CommandType> uriCommandStorage;


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
        uriCommandStorage = new ConcurrentHashMap<>();

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
