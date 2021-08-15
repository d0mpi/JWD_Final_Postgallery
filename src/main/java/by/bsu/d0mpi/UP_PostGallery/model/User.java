package by.bsu.d0mpi.UP_PostGallery.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Calendar;
import java.util.Date;

/**
 * Object of the {@link User} class contains information about user of the web application.
 *
 * @author d0mpi
 * @version 1.0
 * @see DatabaseEntity
 * @see Date
 * @see Calendar
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class User extends DatabaseEntity {
    private String login;
    private String password;
    private Role role;
    private Date createdDate;


    /**
     * Default {@link User} all args constructor
     *
     * @param id user id
     * @param login user login
     * @param password user password
     * @param role user {@link Role}
     * @param registrationDate date of registration of the user
     */
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
