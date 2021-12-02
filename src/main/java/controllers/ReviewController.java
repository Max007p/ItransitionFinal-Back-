package controllers;

import entities.Review;
import entities.response.ReviewResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import services.ReviewService;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
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
    public List<Review> getFilteredReviews(
            @RequestParam(value = "text", required = true) String text) throws InterruptedException {
         return reviewService.getFilteredReviews(text);
    }

}
