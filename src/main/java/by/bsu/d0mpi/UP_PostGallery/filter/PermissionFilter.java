package by.bsu.d0mpi.UP_PostGallery.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

@WebFilter(urlPatterns = "/*")
public class PermissionFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
//        for (AppCommand command : AppCommand.values()) {
//            for (Role allowedRole : command.getAllowedRoles()) {
//                Set<AppCommand> commands = commandsByRoles.computeIfAbsent(allowedRole, k -> EnumSet.noneOf(AppCommand.class));
//                commands.add(command);
//            }
//        }
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }

//    private Role extractRoleFromSession(HttpSession session) {
//        return session != null && session.getAttribute(USER_ROLE_SESSION_ATTRIB_NAME) != null
//                ? (Role) session.getAttribute(USER_ROLE_SESSION_ATTRIB_NAME)
//                : UNAUTHORIZED;
//    }
}
