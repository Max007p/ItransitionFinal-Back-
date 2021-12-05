package repositories;

import entities.Comment;
import entities.Review;
import entities.Tag;
import entities.User;
import entities.response.CommentResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query(value = "SELECT NEW entities.response.CommentResponse(a.id, a.author.id, a.author.userName, a.creation, a.text) " +
            "FROM Comment a WHERE a.review.id = ?1")
    List<CommentResponse> getAllCommentsByReviewId(Long reviewId);

    Comment getCommentByAuthorAndReview(User user, Review review);
}