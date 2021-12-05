package services;

import entities.Comment;
import entities.Review;
import entities.User;
import entities.request.CommentRequest;
import entities.response.CommentResponse;
import entities.response.MessageResponse;
import entities.response.ReviewResponse;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import repositories.CommentRepository;
import repositories.ReviewRepository;
import repositories.UserRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CommentService {
    @Autowired
    CommentRepository commentRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ReviewRepository reviewRepository;

    public List<CommentResponse> getCommentsByReviewId(Long reviewId){
        return commentRepository.getAllCommentsByReviewId(reviewId);
    }

    public ResponseEntity<?> addCommentToReview(CommentRequest comment){
        Optional<User> user = userRepository.findById(comment.getUserId());
        Optional<Review> review = reviewRepository.findById(comment.getReviewId());
        if (user.isPresent() && review.isPresent()){
            commentRepository.save(new Comment(user.get(), comment.getText(), review.get()));
            Comment commentFromDb = commentRepository.getCommentByAuthorAndReview(user.get(), review.get());
            MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
            mapperFactory.classMap(Comment.class, CommentResponse.class);
            MapperFacade mapper = mapperFactory.getMapperFacade();
            CommentResponse commentResponse = mapper.map(commentFromDb, CommentResponse.class);
            return ResponseEntity.ok().body(commentResponse);
        } else{
            return ResponseEntity.badRequest().body(new MessageResponse("No user or review!"));
        }
    }
}
