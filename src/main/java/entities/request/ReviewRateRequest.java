package entities.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewRateRequest {
    private Long userId;
    private Long reviewId;
    private Double userRating;
}
