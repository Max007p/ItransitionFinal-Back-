package entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import enums.NetworksName;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Data
@ToString
@NoArgsConstructor
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

    @ToString.Exclude
    @ManyToMany(fetch = FetchType.LAZY)
    @Cascade({ CascadeType.SAVE_UPDATE, CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private Set<UserRole> roles;

    @JsonIgnore
    @ToString.Exclude
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_review_like",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "review_id", referencedColumnName = "id"))
    private Set<Review> likedReviews;

    @JsonIgnore
    @ToString.Exclude
    @OneToMany(mappedBy = "user")
    private List<UserRatedReview> ratedReviews;

    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }
}
