package entities.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewRateResponse {
    private Long userId;
    private Long reviewId;
    private Double userRating;
}
