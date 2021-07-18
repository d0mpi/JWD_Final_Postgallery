package by.bsu.d0mpi.UP_PostGallery.model;

import java.util.Arrays;
import java.util.List;

public enum Role {
    ADMIN,
    MODERATOR,
    USER,
    UNAUTHORIZED;

    public static Role getRoleByOrdinalNumber(int id) {
        return Role.values()[id - 1];
    }

    public static List<Role> valuesAsList() {
        return Arrays.asList(values());
    }
}
