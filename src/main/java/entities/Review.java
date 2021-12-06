package entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;
import org.hibernate.search.annotations.TermVector;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Entity
@Data
@Indexed
@DynamicUpdate
@Table(name = "review")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Field(termVector = TermVector.YES)
    @Column(name = "title")
    private String title;

    @Field(termVector = TermVector.YES)
    @Column(name = "text")
    private String text;

    @Column(name = "review_rating")
    private double reviewRating;

    @CreationTimestamp
    @Temporal(TemporalType.DATE)
    @Column(name = "creation_date", updatable = false)
    private Date creation;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", updatable = false)
    private User author;

    @IndexedEmbedded
    @OneToMany(mappedBy = "review")
    @Cascade(CascadeType.ALL)
    private List<Comment> comments;

    @ManyToMany(fetch = FetchType.LAZY)
    @Cascade(CascadeType.ALL)
    @JoinTable(
            name = "review_group",
            joinColumns = @JoinColumn(name = "review_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "group_id", referencedColumnName = "id"))
    private Set<Group> reviewGroups = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @Cascade(CascadeType.ALL)
    @JoinTable(
            name = "review_tag",
            joinColumns = @JoinColumn(name = "review_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id", referencedColumnName = "id"))
    private Set<Tag> reviewTags = new HashSet<>();

    @ManyToMany(mappedBy = "likedReviews")
    private List<User> usersWhoLiked = new ArrayList<>();

    @ToString.Exclude
    @OneToMany(mappedBy = "review")
    @Cascade(CascadeType.ALL)
    private List<UserRatedReview> usersWhoRated = new ArrayList<>();

    public void removeUserWhoLiked(User user){
        this.getUsersWhoLiked().remove(user);
        user.getLikedReviews().remove(this);
    }

    public void addUserWhoLiked(User user){
        this.getUsersWhoLiked().add(user);
        user.getLikedReviews().add(this);
    }

    public void removeRatedReviews(UserRatedReview user){
        this.getUsersWhoRated().remove(user);
    }

    public void addRatedReviews(UserRatedReview user){
        this.getUsersWhoRated().add(user);
    }

}
