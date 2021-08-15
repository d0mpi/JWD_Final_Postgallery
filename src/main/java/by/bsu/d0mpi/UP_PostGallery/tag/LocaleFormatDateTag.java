package by.bsu.d0mpi.UP_PostGallery.tag;

import lombok.Setter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Pattern;

/**
 * Custom tag which is responsible for formatting the date depending on {@link Cookie}
 *
 * @author d0mpi
 * @version 1.0
 * @see PageContext
 * @see Cookie
 * @see Locale
 * @see SimpleDateFormat
 * @see JspWriter
 * @see HttpServletRequest
 */
public class LocaleFormatDateTag extends SimpleTagSupport {

    @Setter
    private Date date;

    StringWriter sw = new StringWriter();

    @Override
    public void doTag() throws IOException {
        PageContext pageContext = (PageContext) getJspContext();
        HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
        Cookie[] cookies = request.getCookies();
        Cookie localeCookie = null;
        if (cookies != null)
            localeCookie = Arrays.stream(cookies).filter((cookie) -> (cookie.getName().equals("language"))).findFirst().orElse(null);
        String localeString = "en_US";
        if (localeCookie != null) {
            localeString = localeCookie.getValue();
        }
        Locale locale;
        SimpleDateFormat dateFormat;
        String pattern = "dd MMMM yyyy";
        switch (localeString) {
            case "ru_BY":
                locale = new Locale("ru", "BY");
                break;
            case "zh_CN":
                locale = new Locale("zh", "CN");
                break;
            default:
                locale = new Locale("en", "US");
                break;
        }
        dateFormat = new SimpleDateFormat(pattern, locale);

        String stringDate = dateFormat.format(date);

        JspWriter out = getJspContext().getOut();
        out.println(stringDate);
    }
}
