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
import java.util.Collections;
import java.util.List;

import static by.bsu.d0mpi.UP_PostGallery.command.page.ShowPostEditPage.SESSION_USER_NAME;

/**
 * Implementation of the Command interface, which is responsible
 * for the forwarding to the main page.
 * Implements thread-safe Singleton pattern using double checked locking idiom.
 *
 * @author d0mpi
 * @version 1.0
 * @see Command
 * @see CommandResponse
 * @see CommandRequest
 * @see PostService
 * @see LikeService
 * @see MySQLPageRequest
 */
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

    private ShowMainPage() {
        postService = PostService.simple();
        likeService = LikeService.simple();
        forwardHomePage = new SimpleCommandResponse("WEB-INF/views/index.jsp", false);
    }

    /**
     * Provide a global access point to the instance of the {@link ShowMainPage} class.
     *
     * @return the only instance of the {@link ShowMainPage} class
     */
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

    /**
     * Forwards user to the main page.
     * Checks whether filters and sorting should be applied.
     * Provides pagination of the post feed.
     *
     * @param request the object contains a request received from the client
     * @return an object of the {@link CommandResponse} class with forwarding to the main page.
     */
    @Override
    public CommandResponse execute(CommandRequest request) {
        MySQLPageRequest mySQLPageRequest = buildRequestString(request);

        MyPair<List<Post>, Integer> myPair = postService.getPage(mySQLPageRequest);
        List<Post> postList = myPair.getFirst();
        int pageCount = calculatePageCount(myPair.getSecond());
        retrieveAndDetLikedPostIdList(request);

        request.setAttribute("pageNumber", mySQLPageRequest.getStartNumber() / PAGE_SIZE + 1);
        request.setAttribute("postList", postList);
        request.setAttribute("pageCount", pageCount);
        return forwardHomePage;
    }

    private void retrieveAndDetLikedPostIdList(CommandRequest request) {
        HttpSession session = request.getCurrentSession().orElse(null);
        if (session != null && session.getAttribute(SESSION_USER_NAME) != null) {
            request.setAttribute(LIKED_POSTS_ID_LIST,
                    likeService.getLikedPostIdList((String) session.getAttribute(SESSION_USER_NAME)));
        } else {
            request.setAttribute(LIKED_POSTS_ID_LIST, Collections.emptyList());
        }
    }

    private int calculatePageCount(int numberOfPosts) {
        return Math.max((int) Math.ceil(numberOfPosts / (double) PAGE_SIZE), 1);
    }

    private MySQLPageRequest buildRequestString(CommandRequest request) {
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
        return requestStringBuilder.build();
    }

    private int calculateStartNumber(CommandRequest request) throws NumberFormatException {
        int startNumber = 0;
        if (request.hasParameter("page_number")) {
            startNumber = Integer.parseInt(request.getParameter("page_number"));
            startNumber = Math.max(startNumber, 1);
            startNumber = (startNumber - 1) * 10;
        }
        return startNumber;
    }
}
