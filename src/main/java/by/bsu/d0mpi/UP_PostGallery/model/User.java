package by.bsu.d0mpi.UP_PostGallery.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class User {
    private int id;
    private String login;
    private String password;
}
