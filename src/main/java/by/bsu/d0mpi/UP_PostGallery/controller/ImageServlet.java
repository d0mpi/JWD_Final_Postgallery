package by.bsu.d0mpi.UP_PostGallery.controller;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.file.Files;

/**
 * This servlet finds the necessary image of the post in the server's
 * file system and provides it on response.
 *
 * @author d0mpi
 * @version 1.0
 * @see HttpServletRequest
 * @see HttpServletResponse
 * @see URLDecoder
 * @see Files
 * @see File
 */
@WebServlet("/files/*")
public class ImageServlet extends HttpServlet {

    public static final String IMAGES_UPLOAD_PATH = "b:/Proga/UP_PostGallery_plane_storage/planes";
    public static final String PLANE_IMAGE_POSTFIX = "-card.jpg";


    /**
     * finds the necessary image of the post from request in the server's
     * file system and provides it on response.
     * If the post does not have an image, default.png is provided in th response.
     *
     * @throws IOException if something goes wrong during file reading
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String filename = URLDecoder.decode(request.getPathInfo().substring(1), "UTF-8");
        File file = new File(IMAGES_UPLOAD_PATH, filename);
        if (!file.exists()) {
            file = new File(IMAGES_UPLOAD_PATH, "default.png");
        }
        response.setHeader("Content-Type", getServletContext().getMimeType(filename));
        response.setHeader("Content-Length", String.valueOf(file.length()));
        response.setHeader("Content-Disposition", "inline; filename=\"" + file.getName() + "\"");
        Files.copy(file.toPath(), response.getOutputStream());
    }
}
