package by.bsu.d0mpi.UP_PostGallery.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
public class User extends DatabaseEntity {
    private String login;
    private String password;
    private Role role;
    private Date registrationDate;


    public User(int id, String login, String password, Role role, Date registrationDate) {
        super(id);
        this.login = login;
        this.password = password;
        this.role = role;
        this.registrationDate = registrationDate;
    }

    public User(int id, String login, String password) {
        this(id, login, password, Role.USER, null);
    }

    public User(String login, String password, Role role) {
        this(-1, login, password, role, null);
    }

    public User(String login, String password) {this(-1, login, password, Role.USER, null);}
}
