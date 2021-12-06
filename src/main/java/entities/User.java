package entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import enums.NetworksName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "username", unique = true)
    private String userName;

    @JsonIgnore
    @Column(name = "password")
    private String password;

    @Column(name = "social_network_id")
    private Long socialNetworkId;

    @Enumerated(EnumType.STRING)
    @Column(name = "social_network_name")
    private NetworksName socialNetworkName;

    @Column(name = "social_network_username")
    private String socialNetworkUserName;

    @ManyToMany(fetch = FetchType.LAZY)
    @Cascade({CascadeType.SAVE_UPDATE, CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private Set<UserRole> roles;

    @ManyToMany(fetch = FetchType.LAZY)
    @Cascade({CascadeType.SAVE_UPDATE, CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(
            name = "user_review_like",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "review_id", referencedColumnName = "id"))
    private Set<Review> likedReviews;

    @ToString.Exclude
    @OneToMany(mappedBy = "user")
    @Cascade(CascadeType.ALL)
    private List<UserRatedReview> ratedReviews;

    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public void removeRatedReviews(UserRatedReview user){
        this.getRatedReviews().remove(user);
    }

    public void addRatedReviews(UserRatedReview user){
        this.getRatedReviews().add(user);
    }
}
