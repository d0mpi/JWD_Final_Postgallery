package by.bsu.d0mpi.UP_PostGallery.dao;

import by.bsu.d0mpi.UP_PostGallery.model.User;

public interface UserDao {

    boolean isLoginPresented(String login);

    User findUserByLogin(String login);
}
