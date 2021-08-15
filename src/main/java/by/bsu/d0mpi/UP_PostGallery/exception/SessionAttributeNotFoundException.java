package by.bsu.d0mpi.UP_PostGallery.exception;

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
