package by.bsu.d0mpi.UP_PostGallery.service;

import by.bsu.d0mpi.UP_PostGallery.model.User;
import by.bsu.d0mpi.UP_PostGallery.service.impl.SimpleUserService;

public interface UserService extends DBEntityService<Integer, User>{

    static UserService simple() {
        return SimpleUserService.getInstance();
    }

    boolean canLogIn(User user);

    User findUserByLogin(String login);

    boolean isLoginPresented(String login);
}
