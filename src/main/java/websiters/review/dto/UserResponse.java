package websiters.review.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class UserResponse {
    private UUID  id;
    private String email;
    private boolean active;
}
