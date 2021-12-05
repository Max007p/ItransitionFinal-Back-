package controllers;

import entities.User;
import entities.request.*;
import entities.response.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import services.ReviewService;
import services.TagService;
import services.UserService;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    ReviewService reviewService;

    @GetMapping("/all")
    public List<UserResponse> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/get_review_like")
    public ReviewLikeResponse getReviewLike(
            @RequestParam(value = "userId", required = true) Long userId,
            @RequestParam(value = "reviewId", required = true) Long reviewId
    ) {
        return reviewService.getReviewLike(userId, reviewId);
    }

    @GetMapping("/get_review_rate")
    public ReviewRateResponse getReviewRate(
            @RequestParam(value = "userId", required = true) Long userId,
            @RequestParam(value = "reviewId", required = true) Long reviewId
    ) {
        return reviewService.getReviewRate(userId, reviewId);
    }

    @PostMapping("/add_review")
    public ResponseEntity<MessageResponse> addNewReview(@RequestBody ReviewRequest review) {
        return reviewService.addNewReview(review);
    }

    @PostMapping("/update_review")
    public ResponseEntity<MessageResponse> updateReview(@RequestBody ReviewRequest review) {
        return reviewService.updateReviewById(review);
    }

    @PostMapping("/update_review_like")
    public ReviewLikeResponse updateReviewLike(@RequestBody ReviewLikeRequest reviewLike) {
        return reviewService.updateReviewLike(reviewLike);
    }

    @PostMapping("/update_review_rate")
    public ReviewRateResponse updateReviewRate(@RequestBody ReviewRateRequest reviewRate) {
        return reviewService.updateReviewRate(reviewRate);
    }

    @DeleteMapping("/delete_review/{reviewId}")
    public ResponseEntity<MessageResponse> deleteReview(@PathVariable Long reviewId){
        return reviewService.deleteReviewById(reviewId);
    }
}
