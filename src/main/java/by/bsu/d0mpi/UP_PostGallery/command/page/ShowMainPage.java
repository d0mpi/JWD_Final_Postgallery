package by.bsu.d0mpi.UP_PostGallery.command.page;

import by.bsu.d0mpi.UP_PostGallery.command.Command;
import by.bsu.d0mpi.UP_PostGallery.command.CommandRequest;
import by.bsu.d0mpi.UP_PostGallery.command.CommandResponse;
import by.bsu.d0mpi.UP_PostGallery.command.SimpleCommandResponse;
import by.bsu.d0mpi.UP_PostGallery.model.Post;
import by.bsu.d0mpi.UP_PostGallery.service.LikeService;
import by.bsu.d0mpi.UP_PostGallery.service.MyPair;
import by.bsu.d0mpi.UP_PostGallery.service.PostService;
import by.bsu.d0mpi.UP_PostGallery.util.MySQLPageRequest;
import by.bsu.d0mpi.UP_PostGallery.util.PageSortType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpSession;
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
    public static final String REQUEST_FILTER_SORT_PARAM = "filter_sort";
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
        ArrayList<String> filterParams = new ArrayList<>();

        MySQLPageRequest.Builder requestStringBuilder = MySQLPageRequest.newBuilder();
        if (request.hasParameter(REQUEST_FILTER_AUTHOR_PARAM) && request.hasParameter(REQUEST_FILTER_HASHTAG_PARAM) &&
                request.hasParameter(REQUEST_FILTER_DATE_PARAM)) {
            final String hashtag = request.getParameter(REQUEST_FILTER_HASHTAG_PARAM);
            if (hashtag != null && !hashtag.isEmpty()) {
                requestStringBuilder.hashtag(hashtag);
                request.setAttribute(REQUEST_FILTER_HASHTAG_PARAM, hashtag);
            }

            final String author = request.getParameter(REQUEST_FILTER_AUTHOR_PARAM);
            if (author != null && !author.isEmpty()) {
                requestStringBuilder.author(author);
                request.setAttribute(REQUEST_FILTER_AUTHOR_PARAM, author);
            }

            try {
                String dateString = request.getParameter(REQUEST_FILTER_DATE_PARAM);
                final LocalDate date = LocalDate.parse(dateString);
                requestStringBuilder.date(dateString);
                request.setAttribute(REQUEST_FILTER_DATE_PARAM, date);
            } catch (DateTimeParseException ignored) {
            }
        }

        if (request.hasParameter(REQUEST_FILTER_SORT_PARAM)) {
            String sort = request.getParameter(REQUEST_FILTER_SORT_PARAM);
            requestStringBuilder.sortType(PageSortType.of(sort));
            request.setAttribute(REQUEST_FILTER_SORT_PARAM, sort);
        }

        int startNumber = 0;
        try {
            startNumber = calculateStartNumber(request);
        } catch (NumberFormatException ignored) {
        }
        requestStringBuilder.startNumber(startNumber);
        MyPair<List<Post>, Integer> myPair = postService.getPage(requestStringBuilder.build());

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

    int calculateStartNumber(CommandRequest request) throws NumberFormatException {
        int startNumber = 0;
        if (request.hasParameter("page_number")) {
            startNumber = Integer.parseInt(request.getParameter("page_number"));
            startNumber = Math.max(startNumber, 1);
            startNumber = (startNumber - 1) * 10;
        }
        return startNumber;
    }
}
