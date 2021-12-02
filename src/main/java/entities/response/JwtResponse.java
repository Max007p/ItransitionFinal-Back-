package entities.response;

import lombok.Data;
import java.util.List;

@Data
public class JwtResponse {
    private String token;
    private String type = "Bearer";
    private Long id;
    private String name;
    private List<String> roles;

    public JwtResponse(String token, Long id, String name, List<String> roles) {
        this.token = token;
        this.id = id;
        this.name = name;
        this.roles = roles;
    }
}