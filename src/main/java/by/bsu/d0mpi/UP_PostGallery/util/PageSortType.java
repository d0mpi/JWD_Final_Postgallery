package by.bsu.d0mpi.UP_PostGallery.util;

import lombok.Getter;

public enum PageSortType {
    LENGTH("posts.post_length"),
    WINGSPAN("posts.post_wingspan"),
    HEIGHT("posts.post_height"),
    CREW("posts.post_crew"),
    SPEED("posts.post_speed"),
    DISTANCE("posts.post_distance"),
    PRICE("posts.post_price"),
    DATE("posts.post_create_date");

    @Getter
    private final String columnName;

    PageSortType(String columnName) {
        this.columnName = columnName;
    }

}
