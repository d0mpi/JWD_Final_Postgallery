package by.bsu.d0mpi.UP_PostGallery.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Calendar;
import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
public class User extends DatabaseEntity {
    private String login;
    private String password;
    private Role role;
    private Date createdDate;


    public User(int id, String login, String password, Role role, Date registrationDate) {
        super(id);
        this.login = login;
        this.password = password;
        this.role = role;
        this.createdDate = registrationDate;
    }

    @SuppressWarnings( "deprecation" )
    public User(int id, String login, String password) {
        this(id, login, password, Role.USER, new Date(2021, Calendar.JANUARY, 1));
    }

    @SuppressWarnings( "deprecation" )
    public User(String login, String password, Role role) {
        this(-1, login, password, role, new Date(2021, Calendar.JANUARY, 1));
    }

    public User(String login, String password, Date date) {
        this(-1, login, password, Role.USER, date);
    }
}
