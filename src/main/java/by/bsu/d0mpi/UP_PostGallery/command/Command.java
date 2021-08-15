package by.bsu.d0mpi.UP_PostGallery.command;

/**
 * @author d0mpi
 * @version 1.0
 * @see CommandType
 * @see CommandRequest
 * @see CommandResponse
 */
public interface Command {
    CommandResponse execute(CommandRequest request);

    static Command withName(String name) {
        return CommandType.of(name)
                .getCommand();
    }
}
