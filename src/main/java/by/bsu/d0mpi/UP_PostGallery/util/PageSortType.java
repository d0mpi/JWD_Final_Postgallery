package by.bsu.d0mpi.UP_PostGallery.util;

import by.bsu.d0mpi.UP_PostGallery.command.CommandType;
import lombok.Getter;

/**
 * Storage af all possible sort types.
 *
 * @author d0mpi
 * @version 1.0
 * @see MySQLPageRequest
 */
public enum PageSortType {
    LENGTH("posts.post_length", false),
    WINGSPAN("posts.post_wingspan", false),
    HEIGHT("posts.post_height", false),
    CREW("posts.post_crew", false),
    SPEED("posts.post_speed", false),
    DISTANCE("posts.post_distance", false),
    PRICE("posts.post_price", false),
    DATE("posts.post_create_date", false),
    LENGTH_DESC("posts.post_length", true),
    WINGSPAN_DESC("posts.post_wingspan", true),
    HEIGHT_DESC("posts.post_height", true),
    CREW_DESC("posts.post_crew", true),
    SPEED_DESC("posts.post_speed", true),
    DISTANCE_DESC("posts.post_distance", true),
    PRICE_DESC("posts.post_price", true),
    DATE_DESC("posts.post_create_date", true);

    @Getter
    private final String columnName;

    private final Boolean descending;

    public Boolean isDescending() {
        return this.descending;
    }

    PageSortType(String columnName, Boolean isDescending) {
        this.columnName = columnName;
        this.descending = isDescending;
    }

    /**
     * Allows you to find the sorting type in the storage by name.
     *
     * @param name name of sort type
     * @return type of sorting depending on the name, if type with this name does not exists
     * it returns {@link #DATE_DESC}
     */
    public static PageSortType of(String name) {
        for (PageSortType sortType : values()) {
            if (sortType.name().equalsIgnoreCase(name)) {
                return sortType;
            }
        }
        return DATE_DESC;
    }
}
