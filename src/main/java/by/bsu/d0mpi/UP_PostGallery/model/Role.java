package by.bsu.d0mpi.UP_PostGallery.model;

public enum Role {
    ADMINISTRATOR(1),
    MODERATOR(2),
    DEFAULT(3);

    private final int index;
    Role(int i) {
        index = i;
    }
    public int getIndex(){
        return index;
    }

    public static Role getRoleById(int id) {
        for (Role role : values()) {
            if (role.getIndex() == id) {
                return role;
            }
        }
        return null;
    }
}
