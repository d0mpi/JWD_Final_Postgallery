package by.bsu.d0mpi.UP_PostGallery.filter;

import by.bsu.d0mpi.UP_PostGallery.command.CommandType;
import by.bsu.d0mpi.UP_PostGallery.model.Role;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static by.bsu.d0mpi.UP_PostGallery.controller.ApplicationController.COMMAND_PARAM_NAME;
import static by.bsu.d0mpi.UP_PostGallery.model.Role.UNAUTHORIZED;

@WebFilter(urlPatterns = "/*")
public class PermissionFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        final HttpServletRequest request = (HttpServletRequest) servletRequest;
        final CommandType commandType = CommandType.of(request.getParameter(COMMAND_PARAM_NAME));
        final HttpSession session = request.getSession(false);
        Role currentRole = extractRoleFromSession(session);
        if (commandType.getAllowedRoles().contains(currentRole)) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            ((HttpServletResponse) servletResponse).sendRedirect("/controller?command=main_page");
        }
    }

    private Role extractRoleFromSession(HttpSession session) {
        return session != null && session.getAttribute("current_role") != null
                ? (Role) session.getAttribute("current_role")
                : UNAUTHORIZED;
    }
}
