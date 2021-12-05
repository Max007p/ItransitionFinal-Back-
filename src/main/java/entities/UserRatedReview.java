package entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@IdClass(UserRatedReviewId.class)
@Table(name = "user_review_rate")
public class UserRatedReview implements Serializable{

    @Column(name = "rating", precision=10, scale=2)
    private Double rating;

    @Id
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="user_id", referencedColumnName = "id")
    private User user;

    @Id
    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="review_id", referencedColumnName = "id")
    private Review review;

}
