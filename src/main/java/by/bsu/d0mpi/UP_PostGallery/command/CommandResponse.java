package by.bsu.d0mpi.UP_PostGallery.command;

public interface CommandResponse {

    String getPath();

    boolean isRedirect();

    boolean doNothing();

}
