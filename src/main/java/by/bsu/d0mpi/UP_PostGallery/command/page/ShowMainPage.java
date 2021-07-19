package by.bsu.d0mpi.UP_PostGallery.command.page;

import by.bsu.d0mpi.UP_PostGallery.command.Command;
import by.bsu.d0mpi.UP_PostGallery.command.CommandRequest;
import by.bsu.d0mpi.UP_PostGallery.command.CommandResponse;
import by.bsu.d0mpi.UP_PostGallery.command.SimpleCommandResponse;
import by.bsu.d0mpi.UP_PostGallery.dao.impl.MySqlPostDao;
import by.bsu.d0mpi.UP_PostGallery.model.Post;
import by.bsu.d0mpi.UP_PostGallery.service.PostService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.RequestDispatcher;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ShowMainPage implements Command {
    private static final Logger LOGGER = LogManager.getLogger();
    private static volatile ShowMainPage instance;

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
        List<Post> postList = PostService.simple().findAll();

        if (request.hasParameter("filter_author_text") && request.hasParameter("filter_hashtags_text") &&
                request.hasParameter("filter_date_text")) {
            Stream<Post> postStream = postList.stream();
            final String author = request.getParameter("filter_author_text");
            if (author != null && !author.isEmpty()) {
                postStream = postStream.filter(o -> o.getAuthor().equals(author));
            }

            final String hashtag = request.getParameter("filter_hashtags_text");
            if (hashtag != null && !hashtag.isEmpty()) {
                postStream = postStream.filter(o -> o.getHashtags().contains(hashtag));
            }

            try {
                final LocalDate date = LocalDate.parse(request.getParameter("filter_date_text"));
                postStream = postStream.filter(o -> o.getCreatedAt().isEqual(date));
            } catch (DateTimeParseException ignored) {
            }
            postList = postStream.collect(Collectors.toList());
        }

        request.setAttribute("postList", postList);
        return new SimpleCommandResponse("WEB-INF/views/index.jsp", false);
    }
}
