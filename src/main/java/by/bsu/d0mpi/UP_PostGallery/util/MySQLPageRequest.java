package by.bsu.d0mpi.UP_PostGallery.util;

import lombok.Getter;

public class MySQLPageRequest implements PageRequest {
    public static final String DEFAULT_START_PART = "SELECT * FROM posts";
    public static final String COUNT_START_PART = "SELECT COUNT(posts.post_id) FROM posts";
    @Getter
    private final int startNumber;
    @Getter
    private final String authorFilter;
    @Getter
    private final String dateFilter;
    @Getter
    private final String hashtagFilter;
    @Getter
    private final String sortType;
    @Getter
    private final Boolean isDescendingSort;

    public MySQLPageRequest(String authorFilter, String dateFilter, String hashtagFilter, PageSortType sortType, Boolean isDescendingSort, int startNumber) {
        this.authorFilter = authorFilter;
        this.dateFilter = dateFilter;
        this.hashtagFilter = hashtagFilter;
        this.sortType = sortType.getColumnName();
        this.isDescendingSort = isDescendingSort;
        this.startNumber = startNumber;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    private String buildRequestFilterPart() {
        StringBuilder requestString = new StringBuilder();
        boolean isFirstFilter = true;
        if (!hashtagFilter.isEmpty()) {
            requestString.append(" LEFT JOIN hashtags ON post_id = hashtags_post_id ");
            requestString.append(" WHERE ").append("hashtag_text").append(" = ? ");
            isFirstFilter = false;
        }
        if (!authorFilter.isEmpty()) {
            if (isFirstFilter)
                requestString.append(" WHERE ");
            else
                requestString.append(" AND ");
            requestString.append("posts.post_author").append(" = ? ");
            isFirstFilter = false;
        }
        if (!dateFilter.isEmpty()) {
            if (isFirstFilter) {
                requestString.append(" WHERE ");
            } else {
                requestString.append(" AND ");
            }
            requestString.append("posts.post_create_date").append(" BETWEEN ? AND ? ");
        }
        return requestString.toString();
    }

    private String buildRequestSortedPart() {
        StringBuilder requestString = new StringBuilder();
        if (sortType != null) {
            requestString.append(" ORDER BY ").append(sortType);
            if (this.isDescendingSort)
                requestString.append(" DESC ");
        }
        requestString.append(" LIMIT ?, 10 ");
        return requestString.toString();
    }

    public String getPageRequestString() {
        return DEFAULT_START_PART + buildRequestFilterPart() + buildRequestSortedPart();
    }

    public String getPostsCountRequestString() {
        return COUNT_START_PART + buildRequestFilterPart();
    }

    public static class Builder {
        private int startNumber = 1;
        private String authorFilter = "";
        private String dateFilter = "";
        private String hashtagFilter = "";
        private PageSortType sortType = PageSortType.DATE;
        private Boolean isDescendingSort = true;

        private Builder() {
        }

        public Builder startNumber(int startNumber) {
            this.startNumber = startNumber;
            return this;
        }

        public Builder author(String author) {
            this.authorFilter = author;
            return this;
        }

        public Builder date(String date) {
            this.dateFilter = date;
            return this;
        }

        public Builder hashtag(String hashtag) {
            this.hashtagFilter = hashtag;
            return this;
        }

        public Builder sortType(PageSortType sortType) {
            this.sortType = sortType;
            this.isDescendingSort = sortType.isDescending();
            return this;
        }

        public MySQLPageRequest build() {
            return new MySQLPageRequest(authorFilter, dateFilter, hashtagFilter, sortType, isDescendingSort, startNumber);
        }
    }

}
