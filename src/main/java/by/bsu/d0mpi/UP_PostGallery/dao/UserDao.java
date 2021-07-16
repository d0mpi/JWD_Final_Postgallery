package by.bsu.d0mpi.UP_PostGallery.dao;

import by.bsu.d0mpi.UP_PostGallery.dao.impl.MySqlUserDao;
import by.bsu.d0mpi.UP_PostGallery.model.User;

public interface UserDao extends Dao<Integer, User>{

    boolean isLoginPresented(String login);

    User findUserByLogin(String login);

    static MySqlUserDao MySQL() {
        return MySqlUserDao.getInstance();
    }
}
