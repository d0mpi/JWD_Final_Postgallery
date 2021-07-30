package by.bsu.d0mpi.UP_PostGallery.service;

import by.bsu.d0mpi.UP_PostGallery.model.User;
import by.bsu.d0mpi.UP_PostGallery.service.impl.SimpleUserService;

import java.util.Optional;

public interface UserService extends DBEntityService<Integer, User>{

    static UserService simple() {
        return SimpleUserService.getInstance();
    }

    boolean canLogIn(User user);

    Optional<User> findUserByLogin(String login);

    boolean isLoginPresented(String login);


    int getNumberOfPosts(String login);

    int getRating(String login);

    User changePassword(User user);
}
