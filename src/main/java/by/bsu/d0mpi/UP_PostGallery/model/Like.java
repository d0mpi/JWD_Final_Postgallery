package by.bsu.d0mpi.UP_PostGallery.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public class Like extends DatabaseEntity{
    private int postId;
    private String authorLogin;

    public Like (int id, int postId, String authorLogin){
        super(id);
        this.postId = postId;
        this.authorLogin = authorLogin;
    }

}