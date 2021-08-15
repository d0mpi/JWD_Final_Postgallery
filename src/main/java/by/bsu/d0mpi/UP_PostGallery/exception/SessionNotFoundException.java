package by.bsu.d0mpi.UP_PostGallery.exception;

public class SessionNotFoundException extends Exception{
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
