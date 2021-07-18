package by.bsu.d0mpi.UP_PostGallery.command;

public class SimpleCommandResponse implements CommandResponse{
    private final String path;
    private final boolean redirect;

    public SimpleCommandResponse(String path, boolean redirectFlag) {
        this.path = path;
        this.redirect = redirectFlag;
    }

    @Override
    public String getPath() {
        return path;
    }

    @Override
    public boolean isRedirect() {
        return redirect;
    }
}
