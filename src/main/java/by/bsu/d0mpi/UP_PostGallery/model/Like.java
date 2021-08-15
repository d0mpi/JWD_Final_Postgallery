package by.bsu.d0mpi.UP_PostGallery.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Object of the {@link Like} class contains the id of the {@link Post}
 * that the {@link Like} belongs to and the username of the user who put this like.
 *
 * @author d0mpi
 * @version 1.0
 * @see DatabaseEntity
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public class Like extends DatabaseEntity {
    private int postId;
    private String authorLogin;

    public Like(int id, int postId, String authorLogin) {
        super(id);
        this.postId = postId;
        this.authorLogin = authorLogin;
    }

}