package by.bsu.d0mpi.UP_PostGallery.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "CookieLocaleFilter", urlPatterns = {"/*"})
public class CookieLocaleFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse res = (HttpServletResponse) servletResponse;
        if (req.getParameter("language-select") != null) {
            String languageAbbr;
            switch (req.getParameter("language-select")) {
                case ("RU"):
                    languageAbbr = "ru_BY";
                    break;
                case ("EN"):
                    languageAbbr = "en_US";
                    break;
                case ("CN"):
                    languageAbbr = "zh_CN";
                    break;
                default:
                    languageAbbr = "en_US";
                    break;
            }
            Cookie cookie = new Cookie("language", languageAbbr);
            res.addCookie(cookie);
        } else {
            Cookie[] cookies = req.getCookies();
            if (cookies == null || cookies.length == 0) {
                Cookie cookie = new Cookie("language", "en_US");
                res.addCookie(cookie);
            } else {
                boolean notPresented = true;
                for (Cookie cookie : cookies) {
                    res.addCookie(cookie);
                    if (cookie.getName().equals("language")) notPresented = false;
                }
                if (notPresented) {
                    Cookie cookie = new Cookie("language", "en_US");
                    res.addCookie(cookie);
                }
            }
        }
       filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
    }
}