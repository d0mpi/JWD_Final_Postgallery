package by.bsu.d0mpi.UP_PostGallery.util;

import lombok.Getter;

public class MySQLPageRequest {
    @Getter
    private final String authorFilter;
    @Getter
    private final String dateFilter;
    @Getter
    private final String hashtagFilter;
    @Getter
    private final String sortType;

    private MySQLPageRequest(String authorFilter, String dateFilter, String hashtagFilter, String sortType) {
        this.authorFilter = authorFilter;
        this.dateFilter = dateFilter;
        this.hashtagFilter = hashtagFilter;
        this.sortType = sortType;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public String getFinalRequestString() {
        StringBuilder stringBuilder = new StringBuilder("SELECT * FROM posts");
        return stringBuilder.toString();
    }

    public static class Builder {
        private String authorFilter;
        private String dateFilter;
        private String hashtagFilter;
        private String sortType;

        private Builder() {
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

        public MySQLPageRequest build() {
            return new MySQLPageRequest(authorFilter, dateFilter, hashtagFilter, sortType);
        }
    }
}
