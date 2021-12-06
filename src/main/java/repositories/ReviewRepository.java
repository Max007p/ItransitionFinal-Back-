package repositories;

import entities.Group;
import entities.Review;
import entities.Tag;
import entities.User;
import entities.response.ReviewResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    @Query(value = "SELECT NEW entities.response.ReviewResponse(b.id, AVG(c.rating) AS average_rating, b.reviewRating, b.title, b.text, b.creation) " +
            "FROM Review b LEFT JOIN UserRatedReview c ON b.id = c.review.id GROUP BY b.id ORDER BY average_rating DESC")
    List<ReviewResponse> getPopularReviews(Pageable pageable);

    @Query(value = "SELECT NEW entities.response.ReviewResponse(b.id, AVG(c.rating) AS average_rating, b.reviewRating, b.title, b.text, b.creation) " +
            "FROM Review b LEFT JOIN UserRatedReview c ON b.id = c.review.id GROUP BY b.id ORDER BY b.creation DESC")
    List<ReviewResponse> getLatestReviews(Pageable pageable);

    @Query(value = "SELECT NEW entities.response.ReviewResponse(b.id, AVG(c.rating) AS average_rating, b.reviewRating, b.title, b.text, b.creation) " +
            "FROM Review b LEFT JOIN UserRatedReview c ON b.id = c.review.id WHERE b.author.id = ?1 GROUP BY b.id")
    List<ReviewResponse> getReviewsByAuthorId(Long userId);

    @Query(value = "SELECT AVG(c.rating) FROM Review b LEFT JOIN UserRatedReview c ON b.id = c.review.id WHERE b.id = ?1")
    Double getAvgUsersRating(Long reviewId);

    Boolean existsByUsersWhoLikedAndId(User user, Long reviewId);
    Boolean existsByUsersWhoRatedAndId(User user, Long reviewId);
}
