package controllers;

import entities.Group;
import entities.Tag;
import entities.request.ReviewLikeRequest;
import entities.request.ReviewRequest;
import entities.response.MessageResponse;
import entities.response.ReviewLikeResponse;
import entities.response.ReviewResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import services.ReviewService;

import java.util.List;
import java.util.Set;

@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
@RestController
@RequestMapping("/api/review")
public class ReviewController {
    @Autowired
    ReviewService reviewService;

    @GetMapping("/popular")
    public List<ReviewResponse> getPopularReviews(
            @RequestParam(value = "quantity", required = true) int quantity) {
        return reviewService.getMostPopularReviews(quantity);
    }

    @GetMapping("/latest")
    public List<ReviewResponse> getLatestReviews(
            @RequestParam(value = "quantity", required = true) int quantity) {
        return reviewService.getLatestReviews(quantity);
    }

    @GetMapping("/filter")
    public List<ReviewResponse> getFilteredReviews(
            @RequestParam(value = "text", required = true) String text) throws InterruptedException {
        if (text.equals("")){
            return reviewService.getMostPopularReviews(5);
        }
        else{
            return reviewService.getFilteredReviews(text);
        }
    }

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/my_reviews")
    public List<ReviewResponse> getUsersReviewsList(
            @RequestParam(value = "userId", required = true) Long userId
    ) {
        return reviewService.getUsersReviewsList(userId);
    }

    @GetMapping("/{reviewId}")
    public ReviewResponse getReviewById(@PathVariable Long reviewId) {
        return reviewService.getReviewById(reviewId);
    }

}
