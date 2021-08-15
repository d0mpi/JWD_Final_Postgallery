package by.bsu.d0mpi.UP_PostGallery.exception;

/**
 * Exception that occurs when trying to get a non-existent session attribute.
 *
 * @author d0mpi
 * @version 1.0
 * @see Exception
 */
public class SessionAttributeNotFoundException extends Exception {
    public SessionAttributeNotFoundException() {
        super();
    }

    public SessionAttributeNotFoundException(String message) {
        super(message);
    }

    public SessionAttributeNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
