package by.bsu.d0mpi.UP_PostGallery.command;

public class SimpleCommandResponse implements CommandResponse{
    private final String path;
    private final boolean redirect;
    private final boolean doNothing;

    public SimpleCommandResponse(String path, boolean redirectFlag) {
        this(path, redirectFlag, false);
    }

    public SimpleCommandResponse(String path, boolean redirectFlag, boolean doNothing) {
        this.path = path;
        this.redirect = redirectFlag;
        this.doNothing = doNothing;
    }

    @Override
    public String getPath() {
        return path;
    }

    @Override
    public boolean isRedirect() {
        return redirect;
    }

    @Override
    public boolean doNothing() {
        return doNothing;
    }
}
