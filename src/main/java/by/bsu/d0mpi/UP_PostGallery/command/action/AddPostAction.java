package by.bsu.d0mpi.UP_PostGallery.command.action;

import by.bsu.d0mpi.UP_PostGallery.command.Command;
import by.bsu.d0mpi.UP_PostGallery.command.CommandRequest;
import by.bsu.d0mpi.UP_PostGallery.command.CommandResponse;
import by.bsu.d0mpi.UP_PostGallery.command.SimpleCommandResponse;
import by.bsu.d0mpi.UP_PostGallery.exception.SessionAttributeNotFoundException;
import by.bsu.d0mpi.UP_PostGallery.exception.SessionNotFoundException;
import by.bsu.d0mpi.UP_PostGallery.model.Post;
import by.bsu.d0mpi.UP_PostGallery.model.User;
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
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static by.bsu.d0mpi.UP_PostGallery.command.page.ShowPostEditPage.SESSION_USER_NAME;
import static by.bsu.d0mpi.UP_PostGallery.controller.ImageServlet.IMAGES_UPLOAD_PATH;
import static by.bsu.d0mpi.UP_PostGallery.controller.ImageServlet.PLANE_IMAGE_POSTFIX;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

/**
 * Implementation of the Command interface, which is responsible
 * for adding a {@link Post} to the database and the feed of posts
 * by a registered {@link User} using {@link AddPostAction#execute} method.
 * Implements thread-safe Singleton pattern using double checked locking idiom.
 *
 * @author d0mpi
 * @version 1.0
 * @see Command
 * @see CommandResponse
 * @see PostService
 * @see Part
 * @see HttpSession
 * @see SessionAttributeNotFoundException
 * @see SessionNotFoundException
 */
public class AddPostAction implements Command {

    private static final Logger logger = LogManager.getLogger();
    private static volatile AddPostAction instance;

    private final CommandResponse redirectHomePage;
    private final PostService postService;

    private AddPostAction() {
        redirectHomePage = new SimpleCommandResponse("/controller?command=main_page", true);
        postService = PostService.simple();
    }

    /**
     * Provide a global access point to the instance of the {@link AddPostAction} class.
     *
     * @return the only instance of the {@link AddPostAction} class
     */
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


    /**
     * Parses the post data received from the request, adds the post to the database and the user feed.
     * If an error occurred during the creation of the post, a redirect to the main page also occurs
     *
     * @param request the object contains a request received from the client
     * @return an object of the {@link CommandResponse} class with redirection to the main page
     * after successful or unsuccessful creation of the post
     */
    @Override
    public CommandResponse execute(CommandRequest request) {
        Post post;
        try {
            post = parsePostFromRequest(request);
        } catch (SessionNotFoundException e) {
            logger.error("Session was not found!");
            return redirectHomePage;
        } catch (SessionAttributeNotFoundException e) {
            logger.error("Session attribute with name " + e.getMessage() + " was not found!");
            return redirectHomePage;
        }
        post = postService.createEntity(post);
        try {
            uploadImageToDirectory(request, post.getId());
        } catch (ServletException | IOException e) {
            logger.error("Error during uploading image to the server file system!");
            return redirectHomePage;
        }
        return redirectHomePage;
    }

    private Post parsePostFromRequest(CommandRequest request) throws SessionNotFoundException, SessionAttributeNotFoundException {
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

        List<String> hashtags = parseHashtagsListFromRequest(request);

        HttpSession session = request.getCurrentSession().orElseThrow(SessionNotFoundException::new);

        String author = (String) session.getAttribute(SESSION_USER_NAME);
        if (author == null)
            throw new SessionAttributeNotFoundException(SESSION_USER_NAME);

        return new Post(model, type, length, wingspan, height, origin, crew, speed, distance, price, createdDate, author, hashtags);
    }

    /**
     * Checks whether parameter with hashtags exists in the request received from the client.
     * If the parameter exists this method parses hashtags and adds it to the {@link List},
     * otherwise returns empty {@link ArrayList}
     *
     * @param request the object contains a request received from the client
     * @return {@link List} of hashtags parsed from the request
     */
    protected List<String> parseHashtagsListFromRequest(CommandRequest request) {
        if (request.getParameter("hashtags") != null && !request.getParameter("hashtags").equals("")) {
            return Arrays.stream(
                    request.getParameter("hashtags").
                            split(" ")).distinct().map((string)->(string.replaceAll("#", ""))).collect(Collectors.toList());
        } else {
            return new ArrayList<>();
        }
    }

    /**
     * Uploads the image received from the request to the server's file system.
     * Generates the name of the image file based on the received post ID.
     *
     * @param request the object contains a request received from the client
     * @param postId  id of the {@link Post} received from the client
     * @throws ServletException if something wrong with received file
     * @throws IOException      if something wrong with reading file from request
     */
    protected void uploadImageToDirectory(CommandRequest request, int postId) throws ServletException, IOException {
        Part filePart = request.getPart("file");
        if (filePart.getSize() != 0) {
            File uploads = new File(IMAGES_UPLOAD_PATH);
            File file = new File(uploads, postId + PLANE_IMAGE_POSTFIX);
            try (InputStream input = filePart.getInputStream()) {
                Files.copy(input, file.toPath(), REPLACE_EXISTING);
            }
        }
    }
}
