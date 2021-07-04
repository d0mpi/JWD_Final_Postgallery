package by.bsu.d0mpi.UP_PostGallery.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class User implements DatabaseEntity{
    private int id;
    private String login;
    private String password;
    private Role role;

    public User(String login, String password, Role role) {
        this.login = login;
        this.password = password;
        this.role = role;
    }
}
