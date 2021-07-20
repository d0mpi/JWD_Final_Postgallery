package by.bsu.d0mpi.UP_PostGallery.service;


import by.bsu.d0mpi.UP_PostGallery.model.Like;
import by.bsu.d0mpi.UP_PostGallery.service.impl.SimpleLikeService;

import java.util.List;

public interface LikeService extends DBEntityService<Integer, Like> {

    static LikeService simple() {
        return SimpleLikeService.getInstance();
    }

    void delete(Integer postId, String login);

    List<Integer> getLikedPostIdList(String login);

}
