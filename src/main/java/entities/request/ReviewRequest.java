package entities.request;

import entities.Group;
import entities.Tag;
import enums.ReviewGroups;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewRequest {
    private Long userId;
    private Long reviewId;
    private double reviewRating;
    private String title;
    private String text;
    private List<String> reviewGroups;
    private List<String> reviewTags;

    public ReviewRequest(double reviewRating, String title, String text, List<String> reviewGroups, List<String> reviewTags) {
        this.reviewRating = reviewRating;
        this.title = title;
        this.text = text;
        this.reviewGroups = reviewGroups;
        this.reviewTags = reviewTags;
    }
}
