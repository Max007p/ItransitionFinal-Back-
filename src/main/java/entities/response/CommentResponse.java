package entities.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class CommentResponse {
    private Long commentId;
    private Long userId;
    private String username;
    private Date creation;
    private String text;
}
