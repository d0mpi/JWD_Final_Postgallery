package by.bsu.d0mpi.UP_PostGallery.util;

public interface PageRequest {

    int getStartNumber();

    String getAuthorFilter();

    String getDateFilter();

    String getHashtagFilter();

    String getPageRequestString();

    String getPostsCountRequestString();
}
