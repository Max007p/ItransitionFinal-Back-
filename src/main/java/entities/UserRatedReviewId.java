package entities;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserRatedReviewId implements Serializable {
    private Long user;
    private Long review;
}