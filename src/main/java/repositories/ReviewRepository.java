package repositories;

import entities.Review;
import entities.response.ReviewResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {
    @Query(value = "SELECT NEW entities.response.ReviewResponse(b.id, AVG(c.rating) AS average_rating, b.title, b.text, b.creation) " +
            "FROM Review b, UserRatedReview c WHERE b.id = c.review.id GROUP BY b.id ORDER BY average_rating DESC")
    List<ReviewResponse> getPopularReviews(Pageable pageable);

    @Query(value = "SELECT NEW entities.response.ReviewResponse(b.id, AVG(c.rating) AS average_rating, b.title, b.text, b.creation) " +
            "FROM Review b, UserRatedReview c WHERE b.id = c.review.id GROUP BY b.id ORDER BY b.creation DESC")
    List<ReviewResponse> getLatestReviews(Pageable pageable);
}
