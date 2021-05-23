package by.bsu.d0mpi.UP_PostGallery.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Data
@AllArgsConstructor
public class Post {
    private int id;
    private String model;
    private String type;
    private Float length;
    private Float wingspan;
    private Float height;
    private String origin;
    private Integer crew;
    private Float speed;
    private Float distance;
    private Integer price;
    private Date createdAt;
    private String author;
    private String photoLink;
    private List<String> hashtags;
    private List<String> likeAuthors;
}
