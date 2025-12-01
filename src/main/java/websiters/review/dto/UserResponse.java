package websiters.review.dto;

import lombok.Data;

@Data
public class UserResponse {
    private Long id;
    private String email;
    private boolean active;
}
