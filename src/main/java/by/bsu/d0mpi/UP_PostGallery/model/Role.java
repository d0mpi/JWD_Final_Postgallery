package by.bsu.d0mpi.UP_PostGallery.model;

public enum Role {
    ADMINISTRATOR,
    MODERATOR,
    USER;

    public static Role getRoleByOrdinalNumber(int id) {
        return Role.values()[id];
    }
}
