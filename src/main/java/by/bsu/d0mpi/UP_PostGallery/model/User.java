package by.bsu.d0mpi.UP_PostGallery.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class User extends DatabaseEntity {
    private String login;
    private String password;
    private Role role;


    public User(int id, String login, String password, Role role) {
        super(id);
        this.login = login;
        this.password = password;
        this.role = role;
    }

    public User(int id, String login, String password) {
        this(id, login, password, Role.USER);
    }

    public User(String login, String password, Role role) {
        this(-1, login, password, role);
    }

    public User(String login, String password) {this(-1, login, password, Role.USER);}
}
