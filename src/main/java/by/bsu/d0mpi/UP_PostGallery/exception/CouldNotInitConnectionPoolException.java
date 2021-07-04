package by.bsu.d0mpi.UP_PostGallery.exception;

public class CouldNotInitConnectionPoolException extends Exception {
    public CouldNotInitConnectionPoolException() {
        super();
    }

    public CouldNotInitConnectionPoolException(String message) {
        super(message);
    }

    public CouldNotInitConnectionPoolException(String message, Throwable cause) {
        super(message, cause);
    }
}
