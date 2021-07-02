package by.bsu.d0mpi.UP_PostGallery.dao;

public interface PostDao {

    boolean addLike(Integer postId, String author);

    boolean removeLike(Integer postId, String author);
}
