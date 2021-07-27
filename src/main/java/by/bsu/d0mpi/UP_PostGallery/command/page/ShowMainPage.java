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

public class ShowMainPage implements Command {
    private static final Logger logger = LogManager.getLogger();
    public static final int PAGE_SIZE = 10;
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

        if (request.hasParameter("filter_author_text") && request.hasParameter("filter_hashtags_text") &&
                request.hasParameter("filter_date_text")) {
            final String hashtag = request.getParameter("filter_hashtags_text");
            if (hashtag != null && !hashtag.isEmpty()) {
                filterList.add(FilterType.HASHTAG);
                filterParams.add(request.getParameter("filter_hashtags_text"));
                request.setAttribute("filter_hashtags_text", hashtag);
            }

            final String author = request.getParameter("filter_author_text");
            if (author != null && !author.isEmpty()) {
                filterList.add(FilterType.AUTHOR);
                filterParams.add(request.getParameter("filter_author_text"));
                request.setAttribute("filter_author_text", author);
            }

            try {
                final LocalDate date = LocalDate.parse(request.getParameter("filter_date_text"));
                filterList.add(FilterType.DATE);
                filterParams.add(request.getParameter("filter_date_text"));
                request.setAttribute("filter_date_text", date);
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
        if (session != null && session.getAttribute("user_name") != null) {
            request.setAttribute("likedPostsIdList",
                    likeService.getLikedPostIdList((String) session.getAttribute("user_name")));
        } else {
            request.setAttribute("likedPostsIdList", Collections.emptyList());
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
