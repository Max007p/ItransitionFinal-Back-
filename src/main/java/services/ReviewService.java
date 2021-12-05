package services;

import entities.*;
import entities.request.ReviewLikeRequest;
import entities.request.ReviewRateRequest;
import entities.request.ReviewRequest;
import entities.response.MessageResponse;
import entities.response.ReviewLikeResponse;
import entities.response.ReviewRateResponse;
import entities.response.ReviewResponse;
import org.apache.lucene.search.Query;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import repositories.*;
import security.services.UserDetailsImpl;
import utils.ReviewUtils;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.*;

@Transactional
@Service
public class ReviewService {
    @Autowired
    ReviewRepository reviewRepository;

    @Autowired
    UserRatedReviewRepository userRatedReviewRepository;

    @Autowired
    TagRepository tagRepository;

    @Autowired
    GroupRepository groupRepository;

    @Autowired
    UserRepository userRepository;

    @PersistenceContext
    EntityManager entityManager;

    public List<ReviewResponse> getMostPopularReviews(int pageSize){
        return reviewRepository.getPopularReviews(PageRequest.of(0, pageSize));
    }

    public List<ReviewResponse> getLatestReviews(int pageSize){
        return reviewRepository.getLatestReviews(PageRequest.of(0, pageSize));
    }

    public List<ReviewResponse> getFilteredReviews(String text) throws InterruptedException {
        FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);
        fullTextEntityManager.createIndexer().startAndWait();
        QueryBuilder queryBuilder = fullTextEntityManager.getSearchFactory().buildQueryBuilder()
                .forEntity(Review.class)
                .get();
        Query query = queryBuilder.keyword().onFields("text", "title", "comments.text").matching(text)
                .createQuery();
        FullTextQuery fullTextQuery = fullTextEntityManager.createFullTextQuery(query, Review.class);
        fullTextEntityManager.close();
        List<Review> reviews = (List<Review>) fullTextQuery.getResultList();
        List<ReviewResponse> reviewResponse = new ArrayList<>();
        for (Review review: reviews) {
            reviewResponse.add(ReviewUtils.convertReviewToReviewResponse(review));
        }
        return reviewResponse;
    }

    public List<ReviewResponse> getUsersReviewsList(){
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder
                .getContext().getAuthentication().getPrincipal();
        return reviewRepository.getReviewsByAuthorId(userDetails.getId());
    }

    public ResponseEntity<MessageResponse> addNewReview(ReviewRequest review){
        Review reviewToDb = ReviewUtils.convertReviewRequestToReview(review);
        reviewToDb.setAuthor(userRepository.getById(review.getUserId()));
        Set<Tag> tags = checkTagSetWithDb(reviewToDb.getReviewTags());
        Set<Group> groups = checkGroupSetWithDb(reviewToDb.getReviewGroups());
        reviewToDb.setReviewTags(tags);
        reviewToDb.setReviewGroups(groups);
        reviewRepository.save(reviewToDb);
        return ResponseEntity.ok(new MessageResponse("Review successfully added!"));
    }

    public ResponseEntity<MessageResponse> updateReviewById(ReviewRequest review){
        Optional<Review> fromDb = reviewRepository.findById(review.getReviewId());
        if (fromDb.isPresent()){
            fromDb = Optional.of(ReviewUtils.convertReviewRequestToReview(review));
            Set<Tag> tags = checkTagSetWithDb(fromDb.get().getReviewTags());
            Set<Group> groups = checkGroupSetWithDb(fromDb.get().getReviewGroups());
            fromDb.get().setReviewTags(tags);
            fromDb.get().setReviewGroups(groups);
            reviewRepository.save(fromDb.get());
            return ResponseEntity.ok(new MessageResponse("Review was successfully updated!"));
        }
        else{
            return ResponseEntity.badRequest().body(new MessageResponse("No such review!"));
        }
    }

    public ReviewLikeResponse updateReviewLike(ReviewLikeRequest reviewLike){
        Optional<User> user = userRepository.findById(reviewLike.getUserId());
        Optional<Review> review = reviewRepository.findById(reviewLike.getReviewId());
        boolean ifExists = reviewRepository.existsByUsersWhoLikedAndId(user.get(), review.get().getId());
        if (ifExists){
            review.get().removeUserWhoLiked(user.get());
        } else{
            review.get().addUserWhoLiked(user.get());
        }
        ifExists = !ifExists;
        return new ReviewLikeResponse(ifExists);
    }

    public ReviewLikeResponse getReviewLike(Long userId, Long reviewId){
        Optional<User> user = userRepository.findById(userId);
        Optional<Review> review = reviewRepository.findById(reviewId);
        boolean ifExists = reviewRepository.existsByUsersWhoLikedAndId(user.get(), review.get().getId());
        return new ReviewLikeResponse(ifExists);
    }

    public ReviewRateResponse updateReviewRate(ReviewRateRequest reviewRate){
        Optional<User> user = userRepository.findById(reviewRate.getUserId());
        Optional<Review> review = reviewRepository.findById(reviewRate.getReviewId());
        boolean ifExists = userRatedReviewRepository.existsUserRatedReviewByUserAndReview(user.get(), review.get());
        if (ifExists){
            UserRatedReview reviewFromDb = userRatedReviewRepository.findByUserAndReview(user.get(), review.get());
            reviewFromDb.setRating(reviewRate.getUserRating());
            return new ReviewRateResponse(reviewFromDb.getUser().getId(), reviewFromDb.getReview().getId(), reviewFromDb.getRating());
        } else{
            UserRatedReview newReview = new UserRatedReview(reviewRate.getUserRating(), user.get(), review.get());
            userRatedReviewRepository.save(newReview);
            return new ReviewRateResponse(newReview.getUser().getId(), newReview.getReview().getId(), newReview.getRating());
        }
    }

    public ReviewRateResponse getReviewRate(Long userId, Long reviewId){
        Optional<User> user = userRepository.findById(userId);
        Optional<Review> review = reviewRepository.findById(reviewId);
        boolean ifExists = userRatedReviewRepository.existsUserRatedReviewByUserAndReview(user.get(), review.get());
        if (ifExists){
            UserRatedReview reviewFromDb = userRatedReviewRepository.findByUserAndReview(user.get(), review.get());
            return new ReviewRateResponse(reviewFromDb.getUser().getId(), reviewFromDb.getReview().getId(), reviewFromDb.getRating());
        } else{
            return new ReviewRateResponse();
        }
    }

    public ReviewResponse getReviewById(Long reviewId){
        Review review = reviewRepository.getById(reviewId);
        return ReviewUtils.convertReviewToReviewResponse(review);
    }

    public ResponseEntity<MessageResponse> deleteReviewById(Long reviewId){
        if (reviewRepository.existsById(reviewId))
        {
            reviewRepository.delete(reviewRepository.getById(reviewId));
            return ResponseEntity.ok(new MessageResponse("Review successfully deleted!"));
        } else
            return ResponseEntity.badRequest().body(new MessageResponse("Review wasn't found!"));
    }

    private Set<Tag> checkTagSetWithDb(Set<Tag> tags){
        Set<Tag> checkedTags = new HashSet<>();
        for (Tag tag: tags){
            if (!tagRepository.existsTagByName(tag.getName())){
                tagRepository.save(tag);
            }
            checkedTags.add(tagRepository.findByName(tag.getName()));
        }
        return checkedTags;
    }

    private Set<Group> checkGroupSetWithDb(Set<Group> groups){
        Set<Group> checkedGroups = new HashSet<>();
        for (Group group: groups){
            if (groupRepository.existsByGroups(group.getGroups())){
                checkedGroups.add(groupRepository.findByGroups(group.getGroups()));
            }
        }
        return checkedGroups;
    }

}
