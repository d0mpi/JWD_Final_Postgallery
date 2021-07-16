package by.bsu.d0mpi.UP_PostGallery.service;


import by.bsu.d0mpi.UP_PostGallery.model.Like;
import by.bsu.d0mpi.UP_PostGallery.service.impl.SimpleLikeService;

public interface LikeService extends DBEntityService<Integer, Like> {

    static LikeService simple() {
        return SimpleLikeService.getInstance();
    }
}
