package by.bsu.d0mpi.UP_PostGallery.command;

import by.bsu.d0mpi.UP_PostGallery.command.action.DeletePostAction;
import by.bsu.d0mpi.UP_PostGallery.command.action.RegistrationAction;
import by.bsu.d0mpi.UP_PostGallery.command.action.SignInAction;
import by.bsu.d0mpi.UP_PostGallery.command.action.SignOutPage;
import by.bsu.d0mpi.UP_PostGallery.command.page.*;
import by.bsu.d0mpi.UP_PostGallery.model.Role;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

import static by.bsu.d0mpi.UP_PostGallery.model.Role.ADMIN;
import static by.bsu.d0mpi.UP_PostGallery.model.Role.UNAUTHORIZED;
import static by.bsu.d0mpi.UP_PostGallery.model.Role.USER;
import static by.bsu.d0mpi.UP_PostGallery.model.Role.MODERATOR;

public enum AppCommand {
    ABOUT_PAGE(ShowAboutPage.getInstance()),
    CONTACT_PAGE(ShowContactPage.getInstance()),
    ERROR_PAGE(ShowErrorPage.getInstance()),
    MAIN_PAGE(ShowMainPage.getInstance()),
    POST_ADD_PAGE(ShowPostAddPage.getInstance()),
    POST_EDIT_PAGE(ShowPostEditPage.getInstance()),
    REGISTRATION_PAGE(ShowRegistrationPage.getInstance(), UNAUTHORIZED),
    SIGN_IN_PAGE(ShowSignInPage.getInstance(), UNAUTHORIZED),
    DEFAULT_PAGE(ShowMainPage.getInstance()),

    DELETE_POST(DeletePostAction.getInstance(), USER, ADMIN, MODERATOR),
    REGISTER(RegistrationAction.getInstance(), UNAUTHORIZED),
    SIGN_IN(SignInAction.getInstance(), UNAUTHORIZED),
    SIGN_OUT(SignOutPage.getInstance(), USER, ADMIN, MODERATOR);

    @Getter
    private final Command command;
    @Getter
    private final List<Role> allowedRoles;

    AppCommand(Command command, Role... roles) {
        this.command = command;
        this.allowedRoles = roles != null && roles.length > 0 ? Arrays.asList(roles) : Role.valuesAsList();
    }

    public static AppCommand of(String name) {
        for (AppCommand command : values()) {
            if (command.name().equalsIgnoreCase(name)) {
                return command;
            }
        }
        return DEFAULT_PAGE;
    }
}
