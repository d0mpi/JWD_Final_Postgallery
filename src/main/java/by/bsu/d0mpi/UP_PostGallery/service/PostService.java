package by.bsu.d0mpi.UP_PostGallery.service;

import by.bsu.d0mpi.UP_PostGallery.model.Post;
import by.bsu.d0mpi.UP_PostGallery.service.impl.SimplePostService;
import by.bsu.d0mpi.UP_PostGallery.service.impl.SimpleUserService;

public interface PostService extends DBEntityService<Integer, Post>{

    static PostService simple() {
        return SimplePostService.getInstance();
    }
}
