package entities.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewResponse {
    private Long reviewId;
    private Double rating;
    private String title;
    private String text;
    private Date creation;
}
