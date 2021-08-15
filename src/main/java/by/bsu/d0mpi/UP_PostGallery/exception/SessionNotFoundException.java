package by.bsu.d0mpi.UP_PostGallery.exception;

/**
 * Exception that occurs when trying to get a non-existent session.
 *
 * @author d0mpi
 * @version 1.0
 * @see Exception
 */
public class SessionNotFoundException extends Exception {
    public SessionNotFoundException() {
        super();
    }

    public SessionNotFoundException(String message) {
        super(message);
    }

    public SessionNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
