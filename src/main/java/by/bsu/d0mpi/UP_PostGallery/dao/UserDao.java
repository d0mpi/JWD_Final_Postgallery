package by.bsu.d0mpi.UP_PostGallery.dao;

import by.bsu.d0mpi.UP_PostGallery.dao.impl.MySqlUserDao;
import by.bsu.d0mpi.UP_PostGallery.model.User;

import java.util.List;
import java.util.Optional;

public interface UserDao extends Dao<Integer, User>{

    boolean isLoginPresented(String login);

    Optional<User> findUserByLogin(String login);


    static MySqlUserDao MySQL() {
        return MySqlUserDao.getInstance();
    }

    int getNumberOfPostsByAuthor(String login);

    int getNumberOfLikesByAuthor(String login);
}
