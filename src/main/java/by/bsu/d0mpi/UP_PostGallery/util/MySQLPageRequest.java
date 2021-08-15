package by.bsu.d0mpi.UP_PostGallery.util;

import lombok.Getter;

/**
 * Class is responsible for building request query string with help
 * of builder pattern, needed for the {@link by.bsu.d0mpi.UP_PostGallery.dao.PostDao#getPage(PageRequest)}
 *
 * @author d0mpi
 * @version 1.0
 * @see by.bsu.d0mpi.UP_PostGallery.dao.PostDao
 * @see PageSortType
 */
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

    /**
     * Creates new builder for this class
     *
     * @return builder for this class
     */
    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * @return request query string part with filters
     */
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

    /**
     * @return request query string part with sorting
     */
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

    /**
     * @return request query string start part
     */
    public String getPageRequestString() {
        return DEFAULT_START_PART + buildRequestFilterPart() + buildRequestSortedPart();
    }

    /**
     * @return request query string post count part
     */
    public String getPostsCountRequestString() {
        return COUNT_START_PART + buildRequestFilterPart();
    }

    /**
     * Implementations of builder pattern for {@link MySQLPageRequest}
     */
    public static class Builder {
        private int startNumber = 1;
        private String authorFilter = "";
        private String dateFilter = "";
        private String hashtagFilter = "";
        private PageSortType sortType = PageSortType.DATE;
        private Boolean isDescendingSort = true;

        private Builder() {
        }

        /**
         * Sets number of the start post on the page.
         *
         * @param startNumber number of the start post on the page
         * @return builder object
         */
        public Builder startNumber(int startNumber) {
            this.startNumber = startNumber;
            return this;
        }

        /**
         * Sets author value to request string.
         *
         * @param author author from filter input
         * @return builder object
         */
        public Builder author(String author) {
            this.authorFilter = author;
            return this;
        }

        /**
         * Sets date value to request string.
         *
         * @param date date from filter input
         * @return builder object
         */
        public Builder date(String date) {
            this.dateFilter = date;
            return this;
        }

        /**
         * Sets hashtag value to request string.
         *
         * @param hashtag hashtag from filter input
         * @return builder object
         */
        public Builder hashtag(String hashtag) {
            this.hashtagFilter = hashtag;
            return this;
        }

        /**
         * Sets sort type to request string.
         *
         * @param sortType sort type from sort input
         * @return builder object
         */
        public Builder sortType(PageSortType sortType) {
            this.sortType = sortType;
            this.isDescendingSort = sortType.isDescending();
            return this;
        }

        /**
         * Creates new {@link MySQLPageRequest} instance
         *
         * @return built request query string
         */
        public MySQLPageRequest build() {
            return new MySQLPageRequest(authorFilter, dateFilter, hashtagFilter, sortType, isDescendingSort, startNumber);
        }
    }

}
