package by.bsu.d0mpi.UP_PostGallery.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
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
    private LocalDate createdAt;
    private String author;
    private String photoLink;
    private List<String> hashtags;
    private List<String> likeAuthors;

    public Post(String model, String type, Float length, Float wingspan, Float height, String origin, Integer crew, Float speed, Float distance, Integer price, LocalDate createdAt, String author, String photoLink, List<String> hashtags) {
        this.model = model;
        this.type = type;
        this.length = length;
        this.wingspan = wingspan;
        this.height = height;
        this.origin = origin;
        this.crew = crew;
        this.speed = speed;
        this.distance = distance;
        this.price = price;
        this.createdAt = createdAt;
        this.author = author;
        this.photoLink = photoLink;
        this.hashtags = hashtags;
    }

    public String getHashtagsAsHashString() {
        StringBuilder str = new StringBuilder("");
       for(String hashtag : hashtags){
           str.append("#").append(hashtag).append(" ");
       }
       return str.toString();
    }

    public String getHashtagsAsSpaceString() {
        StringBuilder str = new StringBuilder("");
       for(String hashtag : hashtags){
           str.append(hashtag).append(" ");
       }
       return str.toString();
    }
}
