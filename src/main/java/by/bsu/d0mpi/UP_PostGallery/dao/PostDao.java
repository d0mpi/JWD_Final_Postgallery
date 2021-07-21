package by.bsu.d0mpi.UP_PostGallery.dao;

import by.bsu.d0mpi.UP_PostGallery.dao.impl.MySqlPostDao;
import by.bsu.d0mpi.UP_PostGallery.model.Post;
import by.bsu.d0mpi.UP_PostGallery.service.FilterType;
import by.bsu.d0mpi.UP_PostGallery.service.MyPair;

import java.util.ArrayList;
import java.util.List;

public interface PostDao extends Dao<Integer, Post>{
    static PostDao MySQL() {
        return MySqlPostDao.getInstance();
    }

    List<Post> getPostsByAuthorLogin(String login);

    MyPair<List<Post>, Integer> getPage(int pageNumber, ArrayList<FilterType> filters, ArrayList<String> filterParams);
}
