package by.bsu.d0mpi.UP_PostGallery.command;

public interface Command {
    CommandResponse execute(CommandRequest request);

    static Command withName(String name) {
        return AppCommand.of(name)
                .getCommand();
    }
}
