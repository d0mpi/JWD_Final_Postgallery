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
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.*;
import java.util.stream.Collectors;

import static by.bsu.d0mpi.UP_PostGallery.command.page.ShowPostEditPage.SESSION_USER_NAME;
import static by.bsu.d0mpi.UP_PostGallery.controller.ImageServlet.IMAGES_UPLOAD_PATH;
import static by.bsu.d0mpi.UP_PostGallery.controller.ImageServlet.PLANE_IMAGE_POSTFIX;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;


public class AddPostAction implements Command {
    private static final Logger logger = LogManager.getLogger();
    private static volatile AddPostAction instance;

    private final CommandResponse redirectHomePage;
    private final CommandResponse redirectErrorPage;

    public AddPostAction() {
        redirectHomePage = new SimpleCommandResponse("/controller?command=main_page", true);
        redirectErrorPage = new SimpleCommandResponse("/controller?command=error_page", true);
    }

    public static AddPostAction getInstance() {
        AddPostAction localInstance = instance;
        if (localInstance == null) {
            synchronized (AddPostAction.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new AddPostAction();
                }
            }
        }
        return localInstance;
    }

    @Override
    public CommandResponse execute(CommandRequest request) {
        PostService postService = PostService.simple();

        String model = request.getParameter("model");
        String type = request.getParameter("type");
        BigDecimal length = (request.getParameter("lengthInput").equals("") ||
                request.getParameter("lengthInput") == null) ? new BigDecimal(0) : new BigDecimal(request.getParameter("lengthInput").replace(',', '.'));
        BigDecimal wingspan = (request.getParameter("wingspan").equals("") ||
                request.getParameter("wingspan") == null) ? new BigDecimal(0) : new BigDecimal(request.getParameter("wingspan").replace(',', '.'));
        BigDecimal height = (request.getParameter("height").equals("") ||
                request.getParameter("height") == null) ? new BigDecimal(0) : new BigDecimal(request.getParameter("height").replace(',', '.'));
        String origin = request.getParameter("origin");
        Integer crew = (request.getParameter("crew").equals("") ||
                request.getParameter("crew") == null) ? 0 : Integer.parseInt(request.getParameter("crew"));
        BigDecimal speed = (request.getParameter("speed").equals("") ||
                request.getParameter("speed") == null) ? new BigDecimal(0) : new BigDecimal(request.getParameter("speed").replace(',', '.'));
        BigDecimal distance = (request.getParameter("dist").equals("") ||
                request.getParameter("dist") == null) ? new BigDecimal(0) : new BigDecimal(request.getParameter("dist").replace(',', '.'));
        BigDecimal price = new BigDecimal(request.getParameter("price").replace(',', '.'));
        Date createdDate = new Date();

        HttpSession session = request.getCurrentSession().orElse(null);
        if (session == null) {
            return redirectErrorPage;
        }
        String author = (String) session.getAttribute(SESSION_USER_NAME);

        List<String> hashtags;
        if (request.getParameter("hashtags") != null && !request.getParameter("hashtags").equals("")) {
            hashtags = Arrays.stream(
                    request.getParameter("hashtags").
                            split(" ")).distinct().collect(Collectors.toList());
        } else {
            hashtags = new ArrayList<>();
        }
        Post post = new Post(model, type, length, wingspan, height, origin, crew, speed, distance, price, createdDate, author, hashtags);
        postService.createEntity(post);

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

        return redirectHomePage;
    }
}
