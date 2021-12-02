package entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;
import org.hibernate.search.annotations.TermVector;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Data
@ToString
@Indexed
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

    @CreationTimestamp
    @Temporal(TemporalType.DATE)
    @Column(name = "creation_date")
    private Date creation;

    @JsonIgnore
    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User author;

    @IndexedEmbedded
    @JsonIgnore
    @ToString.Exclude
    @OneToMany(fetch = FetchType.LAZY)
    @Cascade(CascadeType.ALL)
    @JoinColumn(name = "review_id", referencedColumnName = "id")
    private List<Comment> comments;

    @ToString.Exclude
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "review_group",
            joinColumns = @JoinColumn(name = "review_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "group_id", referencedColumnName = "id"))
    private Set<Group> reviewGroups;

    @ToString.Exclude
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "review_tag",
            joinColumns = @JoinColumn(name = "review_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id", referencedColumnName = "id"))
    private Set<Tag> reviewTags;

    @JsonIgnore
    @ToString.Exclude
    @ManyToMany(mappedBy = "likedReviews")
    private List<User> usersWhoLiked;

    @JsonIgnore
    @ToString.Exclude
    @OneToMany(mappedBy = "review")
    @Cascade(CascadeType.ALL)
    private List<UserRatedReview> usersWhoRated;

}
