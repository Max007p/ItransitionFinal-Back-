package entities.response;

import entities.Group;
import entities.Tag;
import enums.ReviewGroups;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewResponse {
    private Long reviewId;
    private Double usersRating;
    private Double reviewRating;
    private String title;
    private String text;
    private Date creation;
    private Collection<String> reviewGroups;
    private Collection<String> reviewTags;

    public ReviewResponse(Long reviewId, Double usersRating, Double reviewRating, String title, String text, Date creation) {
        this.reviewId = reviewId;
        this.usersRating = usersRating;
        this.reviewRating = reviewRating;
        this.title = title;
        this.text = text;
        this.creation = creation;
    }

}
