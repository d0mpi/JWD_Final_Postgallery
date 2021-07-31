package by.bsu.d0mpi.UP_PostGallery.command.page;

import by.bsu.d0mpi.UP_PostGallery.command.Command;
import by.bsu.d0mpi.UP_PostGallery.command.CommandRequest;
import by.bsu.d0mpi.UP_PostGallery.command.CommandResponse;
import by.bsu.d0mpi.UP_PostGallery.command.SimpleCommandResponse;
import by.bsu.d0mpi.UP_PostGallery.model.Post;
import by.bsu.d0mpi.UP_PostGallery.service.FilterType;
import by.bsu.d0mpi.UP_PostGallery.service.LikeService;
import by.bsu.d0mpi.UP_PostGallery.service.MyPair;
import by.bsu.d0mpi.UP_PostGallery.service.PostService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.net.URLDecoder;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static by.bsu.d0mpi.UP_PostGallery.command.page.ShowPostEditPage.SESSION_USER_NAME;

public class ShowMainPage implements Command {
    private static final Logger logger = LogManager.getLogger();
    public static final int PAGE_SIZE = 10;
    public static final String LIKED_POSTS_ID_LIST = "likedPostsIdList";
    public static final String REQUEST_FILTER_AUTHOR_PARAM = "filter_author_text";
    public static final String REQUEST_FILTER_DATE_PARAM = "filter_date_text";
    public static final String REQUEST_FILTER_HASHTAG_PARAM = "filter_hashtags_text";
    private static volatile ShowMainPage instance;
    private final PostService postService;
    private final LikeService likeService;
    private final CommandResponse forwardHomePage;

    public ShowMainPage() {
        postService = PostService.simple();
        likeService = LikeService.simple();
        forwardHomePage = new SimpleCommandResponse("WEB-INF/views/index.jsp", false);
    }

    public static ShowMainPage getInstance() {
        ShowMainPage localInstance = instance;
        if (localInstance == null) {
            synchronized (ShowMainPage.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new ShowMainPage();
                }
            }
        }
        return localInstance;
    }

    @Override
    public CommandResponse execute(CommandRequest request) {
        ArrayList<FilterType> filterList = new ArrayList<>();
        ArrayList<String> filterParams = new ArrayList<>();

        if (request.hasParameter(REQUEST_FILTER_AUTHOR_PARAM) && request.hasParameter(REQUEST_FILTER_HASHTAG_PARAM) &&
                request.hasParameter(REQUEST_FILTER_DATE_PARAM)) {
            final String hashtag = request.getParameter(REQUEST_FILTER_HASHTAG_PARAM);
            if (hashtag != null && !hashtag.isEmpty()) {
                filterList.add(FilterType.HASHTAG);
                filterParams.add(request.getParameter(REQUEST_FILTER_HASHTAG_PARAM));
                request.setAttribute(REQUEST_FILTER_HASHTAG_PARAM, hashtag);
            }

            final String author = request.getParameter(REQUEST_FILTER_AUTHOR_PARAM);
            if (author != null && !author.isEmpty()) {
                filterList.add(FilterType.AUTHOR);
                filterParams.add(request.getParameter(REQUEST_FILTER_AUTHOR_PARAM));
                request.setAttribute(REQUEST_FILTER_AUTHOR_PARAM, author);
            }

            try {
                final LocalDate date = LocalDate.parse(request.getParameter(REQUEST_FILTER_DATE_PARAM));
                filterList.add(FilterType.DATE);
                filterParams.add(request.getParameter(REQUEST_FILTER_DATE_PARAM));
                request.setAttribute(REQUEST_FILTER_DATE_PARAM, date);
            } catch (DateTimeParseException ignored) {
            }
        }

        int startNumber = 0;
        try {
            startNumber = getStartNumber(request);
        } catch (NumberFormatException ignored) {
        }
        MyPair<List<Post>, Integer> myPair = postService.getPage(startNumber, filterList, filterParams);

        List<Post> postList = myPair.getFirst();
        int pageCount = (int) Math.ceil(myPair.getSecond() / (double) PAGE_SIZE);
        pageCount = Math.max(pageCount, 1);


        HttpSession session = request.getCurrentSession().orElse(null);
        if (session != null && session.getAttribute(SESSION_USER_NAME) != null) {
            request.setAttribute(LIKED_POSTS_ID_LIST,
                    likeService.getLikedPostIdList((String) session.getAttribute(SESSION_USER_NAME)));
        } else {
            request.setAttribute(LIKED_POSTS_ID_LIST, Collections.emptyList());
        }

        request.setAttribute("pageNumber", startNumber / PAGE_SIZE + 1);
        request.setAttribute("postList", postList);
        request.setAttribute("pageCount", pageCount);
        return forwardHomePage;
    }

    int getStartNumber(CommandRequest request) throws NumberFormatException {
        int startNumber = 0;
        if (request.hasParameter("page_number")) {
            startNumber = Integer.parseInt(request.getParameter("page_number"));
            startNumber = Math.max(startNumber, 1);
            startNumber = (startNumber - 1) * 10;
        }
        return startNumber;
    }
}
