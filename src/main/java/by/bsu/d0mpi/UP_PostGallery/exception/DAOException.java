package by.bsu.d0mpi.UP_PostGallery.exception;

/**
 * Exception that occurs when something goes wrong in the DAO.
 *
 * @author d0mpi
 * @version 1.0
 * @see Exception
 */
public class DAOException extends Exception {
    public DAOException() {
    }

    public DAOException(String message) {
        super(message);
    }

    public DAOException(String message, Throwable cause) {
        super(message, cause);
    }
}
