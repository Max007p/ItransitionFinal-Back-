package controllers;

import entities.request.CommentRequest;
import entities.request.ReviewRequest;
import entities.response.CommentResponse;
import entities.response.MessageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import services.CommentService;
import services.GroupService;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
@RestController
@RequestMapping("/api/comment")
public class CommentController {
    @Autowired
    CommentService commentService;

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/all")
    public List<CommentResponse> getCommentsByReviewId(@RequestParam(value = "reviewId", required = true) Long reviewId) {
        return commentService.getCommentsByReviewId(reviewId);
    }

    @PreAuthorize("hasAuthority('USER')")
    @PostMapping("/add_comment")
    public ResponseEntity<?> addNewComment(@RequestBody CommentRequest comment) {
        return commentService.addCommentToReview(comment);
    }
}