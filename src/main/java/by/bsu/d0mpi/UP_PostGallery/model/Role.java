package by.bsu.d0mpi.UP_PostGallery.model;

import java.util.Arrays;
import java.util.List;

/**
 * Contains all possible roles of the web application user
 */
public enum Role {
    ADMIN,
    MODERATOR,
    USER,
    UNAUTHORIZED;

    /**
     * Allows you to get the {@link User} {@link Role} by ordinal number
     *
     * @param ordinalNumber an ordinal number of the role in the enum
     * @return the role corresponding to the specified sequence number
     */
    public static Role getRoleByOrdinalNumber(int ordinalNumber) {
        return Role.values()[ordinalNumber - 1];
    }

    /**
     * @return {@link List} of all roles of the web application user
     */
    public static List<Role> valuesAsList() {
        return Arrays.asList(values());
    }
}
