package by.bsu.d0mpi.UP_PostGallery.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

/**
 * Object of the {@link Post} class contains information about post contained in the post feed.
 *
 * @author d0mpi
 * @version 1.0
 * @see DatabaseEntity
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ToString(callSuper = true)
public class Post extends DatabaseEntity {
    private String model;
    private String type;
    private BigDecimal length;
    private BigDecimal wingspan;
    private BigDecimal height;
    private String origin;
    private Integer crew;
    private BigDecimal speed;
    private BigDecimal distance;
    private BigDecimal price;
    private Date createdDate;
    private String author;
    private List<String> hashtags;

    /**
     * Constructor with all arguments
     *
     * @param id post id
     * @param model model of the plane contained in the post
     * @param type type of the plane contained in the post
     * @param length length of the plane contained in the post
     * @param wingspan wingspan of the plane contained in the post
     * @param height height of the plane contained in the post
     * @param origin origin of the plane contained in the post
     * @param crew number of crew members of the plane contained in the post
     * @param speed speed of the plane contained in the post
     * @param distance distance of the plane contained in the post
     * @param price price of the plane contained in the post
     * @param createdDate date of creation of the post
     * @param author author of the post
     * @param hashtags hashtags of the post
     *
     */
    public Post(int id, String model, String type, BigDecimal length, BigDecimal wingspan, BigDecimal height, String origin,
                Integer crew, BigDecimal speed, BigDecimal distance, BigDecimal price, Date createdDate, String author,
                List<String> hashtags) {
        super(id);
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
        this.createdDate = createdDate;
        this.author = author;
        this.hashtags = hashtags;
    }

    /**
     *  Constructor without post ID (-1 instead of post ID)
     *
     * @param model model of the plane contained in the post
     * @param type type of the plane contained in the post
     * @param length length of the plane contained in the post
     * @param wingspan wingspan of the plane contained in the post
     * @param height height of the plane contained in the post
     * @param origin origin of the plane contained in the post
     * @param crew number of crew members of the plane contained in the post
     * @param speed speed of the plane contained in the post
     * @param distance distance of the plane contained in the post
     * @param price price of the plane contained in the post
     * @param createdDate date of creation of the post
     * @param author author of the post
     * @param hashtags hashtags of the post
     */
    public Post(String model, String type, BigDecimal length, BigDecimal wingspan, BigDecimal height, String origin, Integer crew,
                BigDecimal speed, BigDecimal distance, BigDecimal price, Date createdDate, String author, List<String> hashtags) {
        this(-1, model, type, length, wingspan, height, origin, crew, speed, distance, price, createdDate, author, hashtags);
    }

    /**
     * @return a string containing hashtags started by #
     */
    public String getHashtagsAsHashString() {
        StringBuilder str = new StringBuilder();
        for (String hashtag : hashtags) {
            str.append("#").append(hashtag).append(" ");
        }
        return str.toString();
    }

    /**
     * @return a string containing hashtags started by whitespace
     */
    public String getHashtagsAsSpaceString() {
        StringBuilder str = new StringBuilder();
        for (String hashtag : hashtags) {
            str.append(hashtag).append(" ");
        }
        return str.toString();
    }

}
