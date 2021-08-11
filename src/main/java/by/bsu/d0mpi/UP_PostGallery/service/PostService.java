package by.bsu.d0mpi.UP_PostGallery.service;

import by.bsu.d0mpi.UP_PostGallery.model.Post;
import by.bsu.d0mpi.UP_PostGallery.service.impl.SimplePostService;
import by.bsu.d0mpi.UP_PostGallery.util.MySQLPageRequest;
import by.bsu.d0mpi.UP_PostGallery.util.PageRequest;

import java.util.ArrayList;
import java.util.List;

public interface PostService extends DBEntityService<Integer, Post>{

    static PostService simple() {
        return SimplePostService.getInstance();
    }

    boolean postDoesNotBelongToUser(Integer postId, String login);

    MyPair<List<Post>, Integer> getPage(PageRequest pageRequest);
}
