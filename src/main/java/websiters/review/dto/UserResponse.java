package websiters.review.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class UserResponse {
    private Long  id;
    private String email;
    private boolean active;
}
