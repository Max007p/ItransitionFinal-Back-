package repositories;

import entities.Review;
import entities.User;
import entities.UserRatedReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRatedReviewRepository extends JpaRepository<UserRatedReview, Long> {
    Boolean existsUserRatedReviewByUserAndReview(User user, Review review);
    UserRatedReview findByUserAndReview(User user, Review review);
}
