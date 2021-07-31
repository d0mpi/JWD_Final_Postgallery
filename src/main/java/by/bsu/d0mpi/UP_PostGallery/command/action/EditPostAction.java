package by.bsu.d0mpi.UP_PostGallery.command.action;

import by.bsu.d0mpi.UP_PostGallery.command.Command;
import by.bsu.d0mpi.UP_PostGallery.command.CommandRequest;
import by.bsu.d0mpi.UP_PostGallery.command.CommandResponse;
import by.bsu.d0mpi.UP_PostGallery.command.SimpleCommandResponse;
import by.bsu.d0mpi.UP_PostGallery.model.Post;
import by.bsu.d0mpi.UP_PostGallery.service.PostService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static by.bsu.d0mpi.UP_PostGallery.controller.ImageServlet.IMAGES_UPLOAD_PATH;
import static by.bsu.d0mpi.UP_PostGallery.controller.ImageServlet.PLANE_IMAGE_POSTFIX;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

public class EditPostAction implements Command {
    private static final Logger logger = LogManager.getLogger();
    public static final String SESSION_EDIT_POST_ID = "lastEditPostId";
    private static volatile EditPostAction instance;

    private final CommandResponse redirectHomePage;
    private final CommandResponse redirectErrorPage;
    private final PostService postService;

    public EditPostAction() {
        redirectHomePage = new SimpleCommandResponse("/controller?command=main_page", true);
        postService = PostService.simple();
        redirectErrorPage = new SimpleCommandResponse("/controller?command=error_page", true);
    }

    public static EditPostAction getInstance() {
        EditPostAction localInstance = instance;
        if (localInstance == null) {
            synchronized (EditPostAction.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new EditPostAction();
                }
            }
        }
        return localInstance;
    }

    @Override
    public CommandResponse execute(CommandRequest request) {
        HttpSession session = request.getCurrentSession().orElse(null);

        if (session == null) {
            return redirectHomePage;
        }
        Integer postId = (Integer) session.getAttribute(SESSION_EDIT_POST_ID);
        if (postId == null) {
            return redirectHomePage;
        }
        session.removeAttribute(SESSION_EDIT_POST_ID);

        Post post = postService.findEntityById(postId);

        if (post == null) {
            return redirectHomePage;
        }
        post.setModel(request.getParameter("model"));
        post.setType(request.getParameter("type"));
        post.setLength(new BigDecimal(request.getParameter("lengthInput").replace(',', '.')));
        post.setWingspan(new BigDecimal(request.getParameter("wingspan").replace(',', '.')));
        post.setHeight(new BigDecimal(request.getParameter("height").replace(',', '.')));
        post.setOrigin(request.getParameter("origin"));
        post.setCrew(Integer.valueOf(request.getParameter("crew")));
        post.setSpeed(new BigDecimal(request.getParameter("speed").replace(',', '.')));
        System.out.println(new BigDecimal(request.getParameter("speed").replace(',', '.')));
        post.setDistance(new BigDecimal(request.getParameter("dist").replace(',', '.')));
        post.setPrice(new BigDecimal(request.getParameter("price").replace(',', '.')));
        List<String> hashtags;
        if (request.getParameter("hashtags") != null && !request.getParameter("hashtags").equals("")) {
            hashtags = Arrays.stream(
                    request.getParameter("hashtags").
                            split(" ")).distinct().collect(Collectors.toList());
        } else {
            hashtags = new ArrayList<>();
        }

        try {
            Part filePart = request.getPart("file");
            if (filePart.getSize() != 0) {
                File uploads = new File(IMAGES_UPLOAD_PATH);
                File file = new File(uploads, post.getId() + PLANE_IMAGE_POSTFIX);
                try (InputStream input = filePart.getInputStream()) {
                    Files.copy(input, file.toPath(), REPLACE_EXISTING);
                }
            }
        } catch (ServletException | IOException e) {
            e.printStackTrace();
            return redirectErrorPage;
        }

        post.setHashtags(hashtags);
        postService.editEntity(post);

        return redirectHomePage;
    }
}
