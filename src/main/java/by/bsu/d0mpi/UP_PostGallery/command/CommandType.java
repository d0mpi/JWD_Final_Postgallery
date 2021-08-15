package by.bsu.d0mpi.UP_PostGallery.command;

import by.bsu.d0mpi.UP_PostGallery.command.action.*;
import by.bsu.d0mpi.UP_PostGallery.command.page.*;
import by.bsu.d0mpi.UP_PostGallery.model.Role;
import lombok.Getter;

import java.sql.Connection;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static by.bsu.d0mpi.UP_PostGallery.model.Role.ADMIN;
import static by.bsu.d0mpi.UP_PostGallery.model.Role.UNAUTHORIZED;
import static by.bsu.d0mpi.UP_PostGallery.model.Role.USER;
import static by.bsu.d0mpi.UP_PostGallery.model.Role.MODERATOR;

public enum CommandType {
    ABOUT_PAGE(ShowAboutPage.getInstance()),
    CONTACT_PAGE(ShowContactPage.getInstance()),
    ERROR_PAGE(ShowErrorPage.getInstance()),
    MAIN_PAGE(ShowMainPage.getInstance()),
    POST_ADD_PAGE(ShowPostAddPage.getInstance(), USER, ADMIN, MODERATOR),
    POST_EDIT_PAGE(ShowPostEditPage.getInstance(), USER, ADMIN, MODERATOR),
    REGISTRATION_PAGE(ShowRegistrationPage.getInstance(), UNAUTHORIZED),
    USER_PROFILE_PAGE(ShowUserProfilePage.getInstance(), USER, ADMIN, MODERATOR),
    LOGIN_PAGE(ShowLoginPage.getInstance(), UNAUTHORIZED),
    DEFAULT_PAGE(ShowMainPage.getInstance()),

    DELETE_POST(DeletePostAction.getInstance(), USER, ADMIN, MODERATOR),
    ADD_POST(AddPostAction.getInstance(), USER, ADMIN, MODERATOR),
    EDIT_POST(EditPostAction.getInstance(), USER, ADMIN, MODERATOR),
    LIKE(LikeAction.getInstance(), USER, ADMIN, MODERATOR),
    CHANGE_PASSWORD(ChangePasswordAction.getInstance(), USER, ADMIN, MODERATOR),
    CHANGE_LANGUAGE(ChangeLanguageAction.getInstance()),
    SEND_MAIL(SendMailAction.getInstance()),
    REGISTER(RegistrationAction.getInstance(), UNAUTHORIZED),
    SIGN_IN(SignInAction.getInstance(), UNAUTHORIZED),
    SIGN_OUT(SignOutAction.getInstance(), USER, ADMIN, MODERATOR);

    @Getter
    private final Command command;
    @Getter
    private final List<Role> allowedRoles;

    CommandType(Command command, Role... roles) {
        this.command = command;
        this.allowedRoles = roles != null && roles.length > 0 ? Arrays.asList(roles) : Role.valuesAsList();
    }

    public static CommandType of(String name) {
        for (CommandType command : values()) {
            if (command.name().equalsIgnoreCase(name)) {
                return command;
            }
        }
        return DEFAULT_PAGE;
    }
}
